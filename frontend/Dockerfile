# build environment
FROM node:14.10.1-alpine3.10 as build
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
COPY package.json ./
COPY yarn.lock ./
COPY .npmrc ./
COPY .yarnrc ./
RUN yarn install --frozen-lockfile
RUN yarn add react-scripts@3.4.3
ENV NODE_OPTIONS="--max_old_space_size=4096"
RUN echo $NODE_OPTIONS
RUN yarn run env
COPY . ./
RUN yarn run build

# production environment
FROM nginx:stable-alpine
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["sh", "-c", "echo ${CONFIG} > /usr/share/nginx/html/config.json && nginx -g \"daemon off;\""]
