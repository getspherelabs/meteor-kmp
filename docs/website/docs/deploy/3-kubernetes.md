---
title: Deploy to Kubernetes
---

LiveKit streamlines deployment to Kubernetes. We publish a [Helm chart](https://github.com/livekit/livekit-helm) that help you set up a distributed deployment of LiveKit, along with a Service and Ingress to correctly route traffic.

Our Helm chart supports Google GKE and Amazon EKS out of the box, and can serve as a guide on your custom Kubernetes installations.

## Understanding the deployment

LiveKit pods requires direct access to the network with host networking. This means that the rtc.udp/tcp ports that are open on those nodes are directly handled by LiveKit server. With that direct requirement of specific ports, it means we'll be limited to one LiveKit pod per node. It's possible to run other workload on those nodes.

Termination of TLS/SSL is left as a responsibility of the Ingress. Our Helm chart will configure TLS termination for GKE and ALB load balancers. To use ALB on EKS, AWS Load Balancer Controller needs to be [installed separately](https://docs.aws.amazon.com/eks/latest/userguide/aws-load-balancer-controller.html).

![Kubernetes Deployment](/img/deploy/kubernetes.svg)

## Graceful restarts

During an upgrade deployment, older pods will need to be terminated. This could be extremely disruptive if there are active sessions running on those pods. LiveKit handles this by allowing that instance to drain prior to shutting down.

We also set `terminationGracePeriodSeconds` to 5 hours in the helm chart, ensuring Kubernetes gives sufficient time for the pod to gracefully shut down.

## Using the Helm Chart

Ensure [Helm](https://helm.sh/docs/intro/install/) is installed on your machine.

Then add the LiveKit repo

```shell
$ helm repo add livekit https://helm.livekit.io
```

Create a values.yaml for your deployment, using [server-sample.yaml](https://github.com/livekit/livekit-helm/blob/master/server-sample.yaml) as a template.

### Importing SSL Certificates

In order to set up TURN/TLS and HTTPS on the load balancer, you may need to import your SSL certificate(s) into as a Kubernetes Secret. This can be done with:

```shell
kubectl create secret tls <name> --cert <cert-file> --key <key-file> --namespace <namespace>
```

Note, please ensure that the secret is created in the same namespace as the deployment.

### Install & Upgrade

```shell
$ helm install <instance_name> livekit/livekit-server --namespace <namespace> --values values.yaml
```

We'll publish new version of the chart with new server releases. To fetch these updates and upgrade your installation, perform

```shell
$ helm repo update
$ helm upgrade <instance_name> livekit/livekit-server --namespace <namespace> --values values.yaml
```

If any configuration has changed, you may need to trigger a restart of the deployment. Kubernetes triggers a restart only when the pod itself has changed, but does not when the changes took place in the ConfigMap.

### Firewall

Ensure that your [firewall](/deploy/ports-firewall#firewall) is configured properly to allow traffic into LiveKit ports.
