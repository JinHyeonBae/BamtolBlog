FROM node:lts-alpine

WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

COPY front/package.json ./
COPY front/yarn.lock ./

RUN mkdir ./build
COPY front/build ./build

CMD ["npm", "start"]