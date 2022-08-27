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

RUN mkdir -p /etc/letsencrypt/live/bamb.shop

COPY certbot/cert/conf/live/bamb.shop/fullchain.pem /etc/letsencrypt/live/bamb.shop/fullchain.pem
COPY certbot/cert/conf/live/bamb.shop/privkey.pem /etc/letsencrypt/live/bamb.shop/privkey.pem


# RUN touch /init_letsencrypt.sh
# RUN chmod +x /init_letsencrypt.sh
# COPY init_letsencrypt.sh /init_letsencrypt.sh
# RUN /init_letsencrypt.sh

RUN mkdir -p /var/www/html
COPY docker/nginx/index.html /var/www/html/index.html
RUN mkdir -p /var/www/certbot
# RUN restorecon -v -R /etc/letsencrypt