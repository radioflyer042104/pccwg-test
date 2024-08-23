#!/bin/bash
docker-compose down && docker rmi pccwg-test-api && docker rmi pccwg-test-kafka-init && docker rmi pccwg-test-kafka-consumer && docker rmi pccwg-test-web
