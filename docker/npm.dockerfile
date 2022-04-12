FROM node:lts-alpine

WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

COPY front/package.json ./

RUN yarn
RUN yarn add global react-scripts

ENTRYPOINT [ "yarn", "build" ]