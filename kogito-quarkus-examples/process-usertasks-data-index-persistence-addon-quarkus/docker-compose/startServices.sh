#!/bin/sh

echo "Script requires your Kogito Quickstart to be compiled"

PROJECT_VERSION=$(cd ../ && mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

echo "Project version: ${PROJECT_VERSION}"

if [[ $PROJECT_VERSION == *SNAPSHOT ]];
then
  KOGITO_VERSION="latest"
else
  KOGITO_VERSION=${PROJECT_VERSION%.*}
fi

# TODO: Set default to postgresql
DB="infinispan"

if [ -n "$1" ]; then
  if [[ "$1" == "postgresql" || "$1" == "infinispan" ]];
  then
    DB="$1"
    shift 1
  fi
fi

for arg in "$@"
do
   if [[ "$arg" == "all" ]];
   then
     PROFILES="all"
   elif [[ $PROFILES != "all" ]]
     then
       if [[("$arg" == "example") || ("$arg" == "consoles")]]
       then
         PROFILES="$PROFILES$([[ ! -z $PROFILES ]] && echo "," || echo "")$arg"
       fi
   fi
done

echo "Kogito Image version: ${KOGITO_VERSION}"
echo "KOGITO_VERSION=${KOGITO_VERSION}" > ".env"
echo "COMPOSE_PROFILES='${PROFILES}'" >> ".env"

if [ "$(uname)" == "Darwin" ]; then
   echo "DOCKER_GATEWAY_HOST=kubernetes.docker.internal" >> ".env"
elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
   echo "DOCKER_GATEWAY_HOST=172.17.0.1" >> ".env"
fi

if [ ! -d "./persistence" ]
then
  echo "$KOGITO_EXAMPLE_PERSISTENCE does not exist. Have you compiled the project? mvn clean install -DskipTests"
  exit 1
fi
PERSISTENCE_FOLDER=./persistence

if [ ! -d "./svg" ]
then
    echo "$KOGITO_EXAMPLE_SVG_FOLDER does not exist. Have you compiled the project? mvn clean install -DskipTests"
    exit 1
fi

docker compose -f docker-compose-${DB}.yml up