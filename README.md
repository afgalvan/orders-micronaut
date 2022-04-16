# Pedidos-Micronaut

A simple microservice for orders management

## Requirements

- [Docker](https://docs.docker.com/get-docker/)
- [Kubectl](https://kubernetes.io/docs/tasks/tools/)
- [Minikube](https://minikube.sigs.k8s.io/docs/start/)

### Dev requirements

- [SDKMAN](https://sdkman.io/install) (Optional)
- [OpenJDK v17](https://openjdk.java.net/)
- [Gradle v7.4.2](https://gradle.org/install/)
- [PostgreSQL v14](https://www.postgresql.org/download/) (Optional)

## Local run

<details>

<summary>Minikube setup </summary>

- Start minikube

```shell
minikube start
```

- Enable nginx ingress ([1](#further-info))

```shell
minikube addons enable ingress
```

> Verify that the NGINX Ingress controller is running

|           v1.19 or later            |        v1.18.1 or earlier         |
|:-----------------------------------:|:---------------------------------:|
| `kubectl get pods -n ingress-nginx` | `kubectl get pods -n kube-system` |

> Expected output

```shell
NAME                                        READY   STATUS      RESTARTS    AGE
ingress-nginx-admission-create-g9g49        0/1     Completed   0          11m
ingress-nginx-admission-patch-rqp78         0/1     Completed   1          11m
ingress-nginx-controller-59b45fb494-26npt   1/1     Running     0
```

- Start a minikube tunnel ([2](#further-info))

```shell
minikube tunnel
```

</details>

<details>
<summary>Run the application</summary>

- Run all services

```shell
./deploy.sh
```

- Get the IP address

```shell
kubectl get ingress

NAME           CLASS   HOSTS   ADDRESS        PORTS   AGE
jdbc-ingress   nginx   *       123.456.78.9   80      62s
                               ------------
```

- Check the enpoints

http://123.456.78.9/swagger-ui

</details>

## Further info

1. [minikube ingress](https://kubernetes.io/docs/tasks/access-application-cluster/ingress-minikube/)

2. [minikube tunnel](https://minikube.sigs.k8s.io/docs/handbook/accessing/)

