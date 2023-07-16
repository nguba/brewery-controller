Note that you will need an installation of docker desktop in order to run the tests using TestContainers.
You can download it from [here](https://www.docker.com/products/docker-desktop).

## Running the tests

If you encounter errors with the tests using an older docker desktop installation with the following error:

java.lang.NullPointerException: Cannot invoke "java.util.Map.entrySet()" because the return value of "org.testcontainers.shaded.com.github.dockerjava.core.DockerConfigFile.getAuths()" is null

You can fix it by changing the auths parameter from null to the following to your ~/.docker/config.json file:

```json
{
  "auths": {}
}
```

For further information, see [this issue](https://github.com/testcontainers/testcontainers-java/issues/7296