---
title: Distributed Set-up
---

LiveKit is architected to be distributed, with homogeneous instances running across many servers. In distributed mode, Redis is required as shared data store and message bus.

## Multi-node routing

When Redis is configured, LiveKit automatically switches to a distributed setup by using Redis for room data as well as a message bus. In this mode, each node periodically reports their stats to Redis; this enables them to be aware of the entire cluster and make routing decisions based on availability and load. We recommend this setup for a redundant deployment.

When a new room is created, the node that received this request is able to choose an available node from the cluster to host the room.

When a client establishes a signal connection to LiveKit, it creates a persistent WebSocket connection with one of the instances. That instance will then acts as a signaling bridge, proxying messages between the node where the room is hosted and the client.

In a multi-node setup, LiveKit can support a large number of concurrent rooms. However, there are limits to the number of participants in a room since, for now, a room must fit on a single node.

## Multi-region support

It's possible to deploy LiveKit to multiple data centers, allowing users located in different regions to connect to a server that's closest to them.

LiveKit supports this via a [region-aware, load aware node selector](https://github.com/livekit/livekit-server/blob/master/pkg/routing/selector/regionaware.go). It's designed to be used in conjunction with region-aware load balancing of the signal connection.

Here's how it works:

1. Geo or latency aware DNS service (such as Route53 or Cloudflare) returns IP of load balancer closest to the user
2. User connects load balancer in that region
3. Then connects to an instance of LiveKit in that region
4. If the room doesn't already exist, LiveKit will use node selector to choose an available node
5. The selection criteria is
   - node must have lower utilization than `sysload_limit`
   - nodes are in the region closest to the signaling instance
   - a node satisfying the above is chosen at random

### Configuration

```yaml title="config.yaml"
node_selector:
  kind: regionaware
  sysload_limit: 0.5
  # list of regions and their lat/lon coordinates
  regions:
    - name: us-west-2
      lat: 37.64046607830567
      lon: -120.88026233189062
    - name: us-east
      lat: 40.68914362140307
      lon: -74.04445748616385
```
