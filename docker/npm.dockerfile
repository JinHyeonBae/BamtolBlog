FROM node:lts-alpine

WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

COPY front/package.json ./
COPY front/yarn.lock ./