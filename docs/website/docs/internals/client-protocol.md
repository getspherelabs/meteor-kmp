---
title: Client Protocol
---

This is an overview of the core protocol LiveKit uses to communicate with clients. It's primarily oriented towards those building new client SDKs or developers interested in contributing to LiveKit.

:::note

Using LiveKit in your app does not require you to understand the underlying protocol. This is one of our design goals.

:::

## Basics

LiveKit clients use a WebSocket to communicate with the server over Protocol Buffers. Client could establish up to two WebRTC PeerConnections with the SFUs, used for publishing and receiving streams, respectively.

By default, the subscriber PeerConnection will always be open upon connection. The publisher PeerConnection will be established only when the client is ready to publish.

![Client-Server Connection](/img/client-server-connection.svg)

### Protobufs

LiveKit uses Protocol Buffers for all of its communications. Communication happens asynchronously: one side may send a message to the other at any time, without the expectation of an immediate response. LiveKit protobufs reside in the [livekit/protocol repo](https://github.com/livekit/protocol).

As a convention, a client always sends a `SignalRequest` and the server replies with a `SignalResponse`.

### Dedicated PeerConnections

For each client connected to the server, we use up to two separate `PeerConnection` objects. One for publishing tracks to the server, and the other for receiving subscribed tracks.

Using separate peer connections simplifies the negotiation process and eliminates negotiation [Glares](https://www.ietf.org/proceedings/82/slides/rtcweb-10.pdf). The side sending tracks to the other will be the one that initiates the offer.

## Joining a room

1. client initiates WebSocket connection to `/rtc`
2. server sends a `JoinResponse`, which includes room information, the current participant's data, and information about other participants in the room
3. server initiates the subscriber `PeerConnection`, sends `offer` to client
   1. if `AutoSubscribe` is enabled, this offer will contain existing tracks in the room.
4. client and server will exchange ICE candidates via `trickle`
5. client accepts the subscriber connection, sends an `answer`
6. ICE connectivity is established
7. server notifies other participants of the new participant

### WebSocket Parameters

Websocket endpoint `/rtc` is the initial step that the client connects to. It takes in several parameters to give the server information about the client and its capabilities:

* access_token: an encoded JWT access token
* reconnect: true if client is trying to resume to an existing connection. when this is set, server will attempt to perform a ICE restart after connection is established.
* auto_subscribe: true by default. If true, server will automatically subscribe client to all tracks in the room
* sdk: indicates the SDK it's using. (js, ios, android, etc)
* protocol: indicates the protocol version. this document describes the latest protocol version: 3
* version: version of the client SDK

## Publishing

To publish a track, a client must first notify the server of its intent and send up any client-defined metadata about the track.

1. client sends a `AddTrackRequest` with track metadata
2. server sends back a `TrackPublishedResponse`
3. client adds `transceiver` to `PeerConnection`, along with the media track
4. client initiates `offer`, sends to server
5. server answers the offer and starts receiving the track
6. if server subscribes other participants (that has AutoSubscribe enabled) to it

## Receiving tracks

LiveKit server sends down track metadata to all participants in a room as soon as it's published, then it adds the track to each client's subscriber `PeerConnection`.

Since these messages are sent over two, separate communication channels, it's possible for the client to receive a `PeerConnection.onTrack` callback before track metadata is received. If this happens, the client waits to process the track until metadata is received.

## Server events

The client must also be ready to act upon other changes in the room. The server will notify clients of:

* `ParticipantUpdate`: when participants join or leave, or if there are changes to their tracks
* `LeaveRequest`: when the participant should immediately disconnect
* `SpeakersChanged`: when the active speakers in the room changes

### SpeakersChanged

In protocol version 3, server will send down only a list of `SpeakerInfo` that has changed, instead of a comprehensive list of all speakers. Clients are responsible for applying the deltas and firing the appropriate events.

## Client-initiated control

### Mute/unmute local tracks

WebRTC doesn't natively support muting tracks. When a track is disabled, it will continue to periodically send "empty" packets. With LiveKit (and SFUs, in general), we want a discrete mute event in order to notify other participants of the change and to optimize network consumption by suppressing empty packets.

To mute a track, set `MediaStreamTrack.enabled` to false, and subsequently send a `MuteTrackRequest` to the server with that track's `sid`.

### Changing quality of streams

For a particular client, `UpdateTrackSettings` informs the server whether a subscribed track should be temporarily paused, or if the server should send down a stream of differing quality. This is especially useful for larger rooms, when the client wants to optimize how much data it's receiving at once. For example, offscreen clients could have their streams temporarily paused.

### Subscription control

Clients also have the ability to control which tracks they're subscribed to. An `UpdateSubscription` message allows the client to subscribe or unsubscribe to published tracks.
