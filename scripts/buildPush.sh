#!/bin/bash

VERSION=$1

if [[ $VERSION == "" ]]; then
    echo "Missing new version number";
    exit 1;
fi

docker buildx build --platform linux/amd64,linux/arm64 -t ghcr.io/ubunfu/mc-bounty-scraper/mc-bounty-scraper:latest -t ghcr.io/ubunfu/mc-bounty-scraper/mc-bounty-scraper:$VERSION --push .