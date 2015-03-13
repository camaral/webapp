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
