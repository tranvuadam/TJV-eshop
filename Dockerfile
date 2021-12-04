FROM gradle:7-jdk17-alpine
COPY . /src/eshop_backend
WORKDIR /src/eshop_backend

RUN gradle --no-daemon build
CMD gradle --no-daemon bootRun