# TP 3 : Parsing avec Java

Pour parser du XML (et ses dérivés) avec Java, il y a principalement 2 solutions :  

- DOM : javax.xml.parsers.DocumentBuilder
- SAX : org.xml.sax

L'idée est de parser nortre fichier xml et de créer une collection d'objets Beer : 

```java
public class Beer {
    private String id;
    private String name;
    private String description;
    private String img;
    private double alcohol;
    

    @Override
    public String toString() {
        return "Beer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", img='" + img + '\'' +
                ", alcohol=" + alcohol +
                '}';
    }
    
    ...
    
    // getters and setters
```

## DOM 

Le plus simple. Commençons par créer une class `org.giwi.xml.DomParser` :

```java
public class DomParser {

    public List<Beer> getList(File xmlFile) {
        List<Beer> beers = new ArrayList<>();
        // 1 : création d'une factory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 2 : instanciation du parser
        DocumentBuilder builder = factory.newDocumentBuilder();
        // 3 : récupération de l'arbre XML
        Document document= builder.parse(xmlFile);
        // 4 : on part de l'élément racine
        Element racine = document.getDocumentElement();
        // 5 : on récupère les noeuds fils
        NodeList xmlBeers = racine.getChildNodes();
        // et pour chacun d'eux
        for (int i = 0; i<xmlBeers.getLength(); i++) {
            // S'il s'agit d'un noeud
            if(xmlBeers.item(i).getNodeType() == Node.ELEMENT_NODE) {
                // on récupère l'élément XML <beer>
                final Element b = (Element) xmlBeers.item(i);
                Beer beer = new Beer();
                // on traite les sous-éléments, ici, <description> 
                // en y extrayant le contenu
                beer.setDescription(b.getElementsByTagName("description").item(0).getTextContent());
                [...]
                beers.add(beer);
            }
        }
        return beers;
    }
}
```
Puis modifions la classe `org.giwi.xml.Main`

```java
 new DomParser().getList(new File(Main.class.getResource("/beers.xml").toURI())).forEach(System.out::println);
```
> #####Trucs et astuces : 
> Pour avoir un objet `File` à présent dans le classpath : 
> ```java
> File f = new File(
>           // Récupération d'une référence
>           MaClasse.class.getResource("/mon/fichier.txt")
>           // récupération de son path réel
>           .toURI()
>           );
> ```
> 
> En Java 8 ont été introduit les Lambdas
> on peut donc sur certains objets issus de l'API Collection utiliser un forEach à la place
> d'une boucle for : 
> `list.forEach( item -> { System.out.println(item) })`
> on peut également simplifier cette notation :
> `liste.forEach(System.out::println)` cad, on invoque la méthode `println` en lui passant 
> l'item de la liste en argument. On peut ainsi avoir ce type de syntaxe : 
> `list.forEach(this::action)`

Et si on calculait le temps que ça prend?

```java
long start = System.currentTimeMillis();
new DomParser().getList(new File(Main.class.getResource("/beers.xml").toURI())).forEach(System.out::println);
System.out.println("DOM : " + (System.currentTimeMillis() - start) + " ms");
```
Exécutons le code avec

```bash
./gradlew run
```
## SAX

SAX est l'acronyme de Simple API for XML. Ce parseur est événementiel, contrairement à DOM qui
charge l'intégralité de l'arbre XML en mémoire, SAX le traite au fil de l'eau. Attention, c'est
plus compliqué à suivre.

Créons une classe `org.giwi.xml.SaxParser` : 

```java
// Cette classe va implémenter un handler d'événements SAX
public class SaxParser extends DefaultHandler {
    // On a besoin d'attributs d'instance
    private List<Beer> beers;
    private Beer currentBeer;
    private String currentValue;

    // on garde la même signature que pour DOM
    List<Beer> getList(File xmlFile) {
        // on crée une factory
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        // on crée le parseur
        SAXParser saxParser = spf.newSAXParser();
        // on précise un reader
        XMLReader xmlReader = saxParser.getXMLReader();
        // on lui donne le parseur d'événements 
        xmlReader.setContentHandler(this);
        // on lui file le fichier à traiter
        xmlReader.parse(xmlFile.getAbsolutePath());
        return beers;
    }

    // premier événement à traiter
    // on démarrer le parsing du document
    // on en profite pour initialiser notre liste
    @Override
    public void startDocument() throws SAXException {
        beers = new ArrayList<>();
    }

    // Cet événement sert à lire le contenu d'entre 2 balises
    // comme il s'agit d'un buffer, on concatène avec le contenu 
    // précédent
    @Override
    public void characters(char[] caracteres, int debut, int longueur) {
        currentValue += new String(caracteres, debut, longueur);
    }

    // Cet élément est appelé dès que SAX trouve une balise ouvrante  
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        // initialisons le texte qui va se trouver
        // entre l'ouvrante et la fermante
        currentValue = "";
        if ("beer".equals(localName)) {
            // si la balise ouvrante est <beer> on crée un nouvel objet Beer
            currentBeer = new Beer();
        }
    }

    // Cet événement sera appelé sur une balise fermante
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // En fonction de la balise fermante :
        switch (localName) {
            // Si c'est </beer> on ajoute notre objet à la liste
            case "beer" :
                beers.add(currentBeer);
                break;
            // Si c'est <description>
            case "description":
                // on alimente l'objet Beer avec le texte se trouvant entre 
                // la balise ouvrante et la balise fermante
                currentBeer.setDescription(currentValue);
                // A ne pas oublier pour éviter une cascade
                break;
            [...]
            default:
                // A ne pas oublier ;)
                break;
        }
    }
}
```

Puis modifions la classe `org.giwi.xml.Main` et mesurons : 

```java
long start = System.currentTimeMillis();
new SaxParser().getList(new File(Main.class.getResource("/beers.xml").toURI())).forEach(System.out::println);
System.out.println("SAX : " + (System.currentTimeMillis() - start) + " ms");
```

Exécutons le code avec

```bash
./gradlew run
```

## Benchmark SAX vs DOM
Modifions la classe `org.giwi.xml.Main` sans afficher le contenu du fichier XML et mesurons : 

```java
long start = System.currentTimeMillis();
// Parsing avec DOM
new DomParser().getList(new File(Main.class.getResource("/beers.xml").toURI()));
System.out.println("DOM : " + (System.currentTimeMillis() - start) + " ms");

start = System.currentTimeMillis();
// Parsing avec SAX
new SaxParser().getList(new File(Main.class.getResource("/beers.xml").toURI()));
System.out.println("SAX : " + (System.currentTimeMillis() - start) + " ms");
```

Exécutons le code avec

```bash
./gradlew run
```

Alors, c'est qui le boss ?

## La suite

[Par là](../step4)