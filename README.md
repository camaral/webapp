# Basic Web Application
Every REST API project should start here  
- Spring 
- Resteasy
- Spring Data JPA(Hibernate + Derby)
- Dozer
- Jackson2 and jaxb

To create a new project from this one, first go to github and create an empty repo(e.g. 'new-repository'), then do the following.

```bash
$ git clone --bare https://github.com/camaral1/webapp.git
# Make a bare clone of the repository

$ cd webapp.git
$ git push --mirror https://github.com/exampleuser/new-repository.git
# Mirror-push to the new repository

$ cd ..
$ rm -rf webapp.git
# Remove our temporary local repository
```

To run the application

```bash
$ mvn jetty:run
```

To call the sample service either use midas.service.CustomerServiceTest or curl

```bash
$ curl "http://localhost:9095/customer" -X POST -d "{\"firstName\":\"caio\"}" -H "Content-Type: application/json" -H "Accept: application/json"
# {"id":1,"firstName":"caio"}

$ curl -v "http://localhost:9095/customer/1" -H "Accept: application/json"
# {"id":1,"firstName":"caio"}
```