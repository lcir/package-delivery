# Package Delivery 

![Code Coverage](.github/badges/jacoco.svg )

Spring boot CMD application with Docker integration. 

## Build Instructions
### Maven

`You need to have installed maven and Java 16!`

For build, you must run maven binary in shell such as example below. 

```shell
mvn clean package
```

Output of build is self running JAR application placed in `target` folder. 
Name of this JAR will be something such as `package-delivery-0.0.1-SNAPSHOT.jar`.

### Docker

Application is possible to build and run in Docker container. For this is implemented two phase build
process in Docker.

For building just type in shell
```shell
docker build . -t lci/package
```

## Run Instructions
### Shell
For application start is possible use Java runtime and in shell just type

```shell
java -jar package-delivery-0.0.1-SNAPSHOT.jar
```

Application itself has optional two arguments. First argument is absolute file path for 
initial data package.

Format of this file must be something like this below.
```
3.4 08801
2 90005
12.56 08801
5.5 08079
```

Second parameter is also absolute file path. But not for initial file, but for file with 
Fee values.

Format of this file is written below.
```
10 5.00
5 2.50
3 2.00
2 1.50
1 1.00
0.5 0.70
0.2 0.50
```

So if you want start application with these initial data and fee, you must run app like below.
```shell
java -jar package-delivery-0.0.1-SNAPSHOT.jar /path/for/initial.txt /path/for/fee.txt
```

### Docker

Application build by Docker can run in container. If you don't need to use initial file and fee file 
described above, just type:
```shell
docker run -it lci/package
```

If you need to use files, you must map volumes with your files and run command with arguments like this:
```shell
docker run -it -v $PWD/src/test/resources:/file lci/package /file/inputs.txt /file/fee.txt
```

## Use instructions

Application is Spring Boot Application with integrated Spring Integrations Flow.

Application is listening for input from stdin. 
This format of input si strict defined, and none another format is tolerated. Information about right format is 
printed to screen after wrong input is detected.

Also is not tolerated any wrong format in file. In loading file content are all wrong values logged to stderr.

### Right user input format and initial file format is
&lt;weight: positive number, &gt;0, maximal 3 decimal places, . (dot) as decimal
separator&gt;&lt;space&gt;&lt;postal code: fixed 5 digits&gt;

### Right fee input format is
&lt;weight: positive number, &gt;0, maximal 3 decimal places, . (dot) as decimal
separator&gt;&lt;space&gt;&lt;fee: positive number, &gt;=0, fixed two decimals, . (dot) as decimal separator&gt;

### Closing application
If you want exit application, you must type `quit` in stdin. 

## Improvements
* When is weight "report" printed in stdout, not completed user input is interrupted.
* Argument priority is hardcoded, named parameter should be better.
