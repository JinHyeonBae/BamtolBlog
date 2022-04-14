FROM nginx:stable-alpine

ARG UID
ARG GID

ENV UID=${UID}
ENV GID=${GID}

RUN delgroup dialout

RUN addgroup -g ${GID} --system react
RUN adduser -G react --system -D -s /bin/sh -u ${UID} react
RUN sed -i "s/user nginx/user react/g" /etc/nginx/nginx.conf

ADD docker/nginx/default.conf /etc/nginx/conf.d/

RUN mkdir -p /var/www/html