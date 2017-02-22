# TP 2 : Transformation avec Java

## La classe

D'adord créer un package dans `src/main/java`, par exemple `org.giwi.xml`.

Puis une classe `Main.java` :

```java
package org.giwi.xml;

public class Main {
    public static void main(String... args) {
        System.out.println("Hello");
    }
}
```

Ajoutez au `buid.gradle` :

```groovy
mainClassName = 'org.giwi.xml.Main'
```

Vérifiez que tout fonctionne : 

```bash
./gradlew run
    Configuration on demand is an incubating feature.
    :compileJava
    :processResources
    :classes
    :run
    Hello

    BUILD SUCCESSFUL

    Total time: 1.953 secs
```
    
Ok, rentrons dans le dur et utilisons les classes de `javax.xml.transform`
présentes en standard dans le JDK

```java
// Récupérons une factory
TransformerFactory factory = TransformerFactory.newInstance();
// Créons un transformer à partir de notre XSL
Transformer t = factory.newTransformer(new StreamSource(Main.class.getResourceAsStream("/style.xsl")));
// prenons notre XML et produisons un HTML
t.transform(
        new StreamSource(Main.class.getResourceAsStream("/beers.xml")),
        new StreamResult("build/beers.html")
);
```

> #####Trucs et astuces : 
> `Main.class.getResourceAsStream` permet de piocher un fichier présent dans le classpath
> en l'occurrence dans `src/main/resources`

Exécutons le code avec

```bash
./gradlew run
```

et normalement, un fichier `build/beers.html` a été créé, vous pouvez vérifier en l'ouvrant avec un navigateur.
 
> #####Trucs et astuces : 
> Gradle offre des sucres syntaxiques. Par exemple :  `./gradlew run` peut être remplacé par
> `./gradlew r`. Et si vous voulez bien nettoyer avant exécution, il existe une tâche `clean`.
> Vous pouvez même chaîner les tâches (soit directement dans le `build.gradle`, mais ça a peu 
> d'intérêt) dans notre cas, soit en ligne de commande :
> ```bash
> ./gradlew clean r
> ```

## La suite

[C'est là](../step3)