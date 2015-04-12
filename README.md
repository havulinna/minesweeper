# Minesweeper demo project

Technology demo, programming exercise and a tribute to the classic minesweeper game.

## Synopsis

This is a project aiming to implement the same classic game with different web 
technologies. The goal is not to simply port the logic from one implementation to another
but to follow the conventions and best practices of each programming environment.

Minesweeper is a simple game, but it is complex enough to introduce interesting 
differences in how the same problems are approached by different tools and languages.


## Spring MVC

This implementation is a simple "web 1.0" application. The aim of this application was to
write as clean and understandable code as possible.

As the application is written fully in Java, all moves in the game are processed by the 
back-end and each move in the game reloads the whole page. This is more of a technology 
demo than a polished entertainment product, so please do not expect too much from the user
experience.

### Requirements and installation

This application requires Apache Maven 3 build automation tool and Java SE Development 
Kit 8.

To start the server:

```
mvn spring-boot:run
```

When the application is running, you can access it with your browser at 
**http://localhost:8080**.


## AngularJS

This implementation was initially written while studying how to use the AngularJS
framework.

As JavaScript applications often mix the business logic with the user interface logic,
making the applications hard to extend and test, the key design goal here was
to separate these two as much as possible.

### Requirements and installation

This application has no special requirements, it can be run locally by opening the 
```index.html``` page in your browser.
