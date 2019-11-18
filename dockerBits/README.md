# So you made this container, how the fuck do I use it?

Well, you do it like this

1. Create your .war file
2. Drop that .war file into the `code/` directory and name it `myWarFile.war`
3. `docker build -t casino %DirectoryThatHoldsYourDockerfile%`
4. wait for awhile because there's a lot to do on the first build. be patient. get a coffee.
4. `docker run -p 8080:8080 casino:latest`
5. go look at the beauty that you have created in a browser.
