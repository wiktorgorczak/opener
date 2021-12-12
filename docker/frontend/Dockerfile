FROM node:14-alpine
COPY ./package.json  /app/package.json
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
RUN npm install -g
RUN npm install react-scripts -g --silent
CMD ["npm", "start"]
