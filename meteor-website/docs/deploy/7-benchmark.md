---
title: Benchmarking
---

## Measuring performance

LiveKit can scale to many simulteneous rooms by running a distributed setup across multiple nodes. However, each room must fit within a single node. For this reason, benchmarks below will be focused on stressing the number of concurrent users in a room.

With WebRTC SFUs, the main proxy for how much work the server is doing is total fowarded bitrate.

```
Total forwarded bitrate = per track bitrate * tracks published * number of subscribers
```

For example, for a room with 6 publishers, each publishing one video stream at 600kbps, and 100 subscribers, total forwarded bitrate would be ~360Mbps.

We have a couple of different tools to help you understand the limits of your deployment.

### CLI load tester

`livekit-load-tester` comes as part of [livekit-cli](https://github.com/livekit/livekit-cli) repo. It uses our Go SDK to act as either a publisher or a subscriber in a room.

For publishing, it sends packets simulating a particular bitrate. This makes it a good approximation for audio streams, but less so for video. With real video tracks, the publisher responds to downstream feedback such as PLI packets and would then produce I frames.

As a subscriber, it will compute packet loss and produce NACKs, but does not produce PLI.

When benchmarking with the load tester, be sure to run it on a machine with plenty of resources, and that the [sysctl parameters](/deploy/test-monitor#performancetuning) have been tuned.

### Headless chrome

A closer approximation to real world traffic, especially for video, would be to run headless Chrome instances to join the room. We've created a [Chrometester](https://github.com/livekit/chrometester) for this purpose. This docker image will start Chromemium in headless mode, and use our react example app to join a test room.

Chrometester can join a room either as a passive subscriber, or to publish their video and audio (via simulated camera/mic source). To use this properly, you'd want to orchestrate many instances of it at the same time. We have been performing our own tests on a Kubernetes cluster.

## Benchmarks

We've ran benchmarks for a few different scenarios to give a general understanding of performance. All benchmarks were ran with the server running on a 16-core, compute optimized instance on Google Cloud. ( `c2-standard-16`)

Note: in the tables below, `Pubs` indicate number of participants publishing to the room, and `Subs` indicate the number of subscribers (including the publishers). Autosubscribe is enabled in benchmarks so that everyone is subscribing to every publisher.

### Audio only

This simulates an audio only experience with various number of speakers and listeners. It's performed using CLI load tester using a bitrate of 20kbps.

#### Results

| Pubs | Subs | Tracks | Latency | Packet loss |
| :--- | :--- | :----- | :------ | :---------- |
| 10   | 10   | 90     | 46.5ms  | 0.000%      |
| 10   | 110  | 1090   | 47ms    | 0.000%      |
| 50   | 50   | 2450   | 46.7ms  | 0.000%      |
| 10   | 510  | 5090   | 48.3ms  | 0.001%      |
| 100  | 100  | 9900   | 49.2ms  | 0.020%      |
| 10   | 1010 | 10090  | 52.1ms  | 1.529%      |

### Video room

We performed a set of benchmark using the [example react app](https://example.livekit.io/) and Chrometester. The latest run was performed on December 2nd, 2021 with server [v0.14.2](https://github.com/livekit/livekit-server/releases/tag/v0.14.2).

The tests measure two scenarios:

1. few publishers, how many simulteneous subscribers can it handle
2. large meetings assuming everyone is publishing both video and audio.

#### Results

| Pubs | Subs | CPU Usage | Egress (GB/min) |
| :--- | :--- | :-------- | :-------------- |
| 5    | 990  | 90%       | 7.43            |
| 100  | 100  | 65%       | 2.75            |
