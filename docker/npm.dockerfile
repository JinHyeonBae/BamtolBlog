FROM node:lts-alpine

WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

COPY front/package.json ./
COPY front/yarn.lock ./

RUN mkdir ./build
COPY front/build ./build

RUN npm install serve -g

ENTRYPOINT ["serve", "-s", "build"]