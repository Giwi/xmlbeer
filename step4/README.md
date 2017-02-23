# TP 4 : Création d'un WebService SOAP

## HelloWorld

### Préparation

Bon il y a un début à tout. Dans `build.gradle` : 

```groovy
allprojects {
    repositories {
        jcenter()
    }
}
apply plugin: 'java'
apply plugin: 'idea'
apply plugin: 'eclipse'
apply plugin: 'application'

task wrapper(type: Wrapper) {
    gradleVersion = '3.4'
}

mainClassName = 'org.giwi.xml.Main'

dependencies {
    compile 'org.apache.cxf:cxf-bundle:2.7.18'
}
```

### La partie serveur

Créez une interface `org.giwi.xml.server.BeerService` :

```java
@WebService
public interface BeerService {
    String sayHi(@WebParam(name="text") String text);
}
```

Puis son implémentation `org.giwi.xml.server.BeerServiceImpl` :

```java
@WebService
public class BeerServiceImpl implements BeerService {
    public String sayHi(String name) {
        System.out.println("sayHello is called by " + name);
        return "Hello " + name;
    }
}
```

Enfin, on modifie la classe `org.giwi.xml.Main` pour démarrer un serveur :

```java
public class Main {
    public static void main(String... args) throws InterruptedException {
        BeerService beerService = new BeerServiceImpl();
        //create WebService service factory
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        //register WebService interface
        factory.setServiceClass(BeerService.class);
        //publish the interface
        factory.setAddress("http://localhost:9000/BeerService");
        factory.setServiceBean(beerService);
        //create WebService instance
        factory.create();
        System.out.println("Server ready...");
    }
}
```

Oui, je sais, ça ne s'invente pas ;)

```bash
./gradlew run
```

Et vérifiez que vous avez quelque chose [http://localhost:9000/BeerService?wsdl](http://localhost:9000/BeerService?wsdl).

### La partie client

Créez une classe `org.giwi.xml.client.Client` :

```java
public class Client {
    private Client() {
    }
    public static void main(String[] args) {
        //create WebService client proxy factory
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        //register WebService interface
        factory.setServiceClass(BeerService.class);
        //set webservice publish address to factory.
        factory.setAddress("http://localhost:9000/BeerService");
        BeerService beerService = (BeerService) factory.create();
        System.out.println("invoke webservice...");
        System.out.println("message context is:" + beerService.sayHi("Gilbert"));
        System.exit(0);
    }
}
```

Dans `build.gradle` ajouter : 

```groovy
task runClient(type: JavaExec, dependsOn: 'classes') {
    main = 'org.giwi.xml.client.Client'
    classpath = sourceSets.main.runtimeClasspath
}
```

Puis dans un autre terminal (Le serveur étant toujours up) :

```bash
./gradlew runClient
```

## Beer service

Allons plus loin et définissons un ensemble de services pour gérer notre catalogue de bières.
L'idée est de charger notre fichier XML (avec SAX hein!) au démarrage du serveur dans un singleton.

Vous pouvez couper le seerveur (CTRL+C) ;)

Créons ce singleton `org.giwi.xml.data.BeersList` et observez le pattern singleton de type holder :

```java
public class BeersList {
    private List<Beer> beerList = new ArrayList<>();
    // Constructeur privé
    private BeersList() {}

    // Holder
    private static class SingletonHolder {
        // Instance unique non préinitialisée
        private final static BeersList instance = new BeersList();
    }

    // Point d'accès pour l'instance unique du singleton
    public static BeersList getInstance() {
        return SingletonHolder.instance;
    }
    
    // getters / setters
}
```

Ensuite, dans `org.giwi.xml.Main` :

```java
[...]
BeersList.getInstance()
    .setBeerList(new SaxParser().getList(new File(Main.class.getResource("/beers.xml").toURI())));
BeerService beerService = new BeerServiceImpl();
[...]
```

Puis, modifiez `org.giwi.xml.server.BeerService` :

```java
@WebService
public interface BeerService {
   List<Beer> getList();
   Beer getBeer(String id);
   void addBeer(Beer beer);
   void delBeer(String id);
}
```

et implémentez le tout dans `org.giwi.xml.server.BeerServiceImpl`.

Enfin, modifiez `org.giwi.xml.client.Client` pour définir un petit scénario pour tester vos services

## Génération d'un client

Vous avez vu que pour développer le client, in faut avoir le modèle objet exposé sous la main?
Dans la vraie vie, on génère un client à partir du WSDL : 

[Télécharger la distri CXF](http://apache.mediamirrors.org/cxf/2.7.18/apache-cxf-2.7.18.tar.gz) et dé-tar-gézipez la.

Lancez le serveur dans un terminal si ce n'est pas déjà fait.

```bash
apache-cxf-2.7.18/bin/wsdl2java -d generated -client http://localhost:9000/BeerService?wsdl
```

Vous retrouverez une implémentation de client dans `generated/org.giwi.xml.server.BeerService_BeerServicePort_Client`

> #####Trucs et astuces : 
> Avec le JDK8 est apparu quelques restrictions de sécurité, éditez le fichier  
> `apache-cxf-2.7.18/bin/wsdl2java` ou `apache-cxf-2.7.18/bin/wsdl2java.bat` (en fonction de l'OS)
> et ajoutez l'option java `-Djavax.xml.accessExternalSchema=all` juste avant `-Djava.util.logging.config[...]`
> ligne 77 dans le bash et ligne 40 dans le bat.
>
> ```bash
> $JAVA_HOME/bin/java -Xmx128M -Djava.endorsed.dirs="${cxf_home}/lib/endorsed" -cp "${cxf_classpath}" -Djavax.xml.accessExternalSchema=all -Djava.util.logging.config.file=$log_config org.apache.cxf.tools.wsdlto.WSDLToJava "$@"
> ```

## Pour aller plus loin

Il existe de nombreux outils pour tester et requêter des Web-Services SOAP, parmis eux :
 
- Eclipse
- IntelliJ
- [SoapUI](https://www.soapui.org/) -> Testez le ([Aide](https://www.soapui.org/soap-and-wsdl/getting-started.html))

Merci :)