#!/usr/bin/env bash

native-image -cp target/dubbo-cli.jar \
  -H:Name=target/dubbo \
  -H:IncludeResources='help.txt' \
  -H:ReflectionConfigurationFiles=reflection.json \
  -H:+ReportUnsupportedElementsAtRuntime \
  -H:+AddAllCharsets \
  --no-server \
  com.gxk.ext.Main
