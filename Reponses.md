

= TP RDF OWL:
\

== Exercice 2 : 
\
1. Identification des vocabulaires (Vocabularies)

rdf: utilisé pour la structure de base (définir le type des objets).

rdfs: utilisé pour le schéma (créer des hiérarchies de classes et des règles sur les propriétés).

owl: utilisé pour les ontologies complexes (définir précisément la nature des
     propriétés).

movies: vocabulaire local spécifique au domaine du cinéma (films, acteurs, réalisateurs).

dbp: vocabulaire externe (DBpedia) utilisé pour identifier des personnes réelles comme Alfred Hitchcock.


\


2.  
Connaissances Ontologiques (Le Schéma):

  Ces triplets définissent les classes, les hiérarchies et les contraintes sur les propriétés (domaines et portées) qu'on met dans le fichier owlExo2Schema.ttl
  
  ```ttl
  @prefix movies: <http://www.lirmm.fr/ulliana/movies#> .
  @prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
  @prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
  @prefix owl: <http://www.w3.org/2002/07/owl#> .
  movies:directedBy rdfs:domain movies:Movie .
  movies:title rdfs:domain movies:Movie .
  movies:playsIn rdfs:domain movies:Actor .
  movies:playsIn rdfs:range movies:Movie .
  movies:Actor rdfs:subClassOf movies:Artist .
  movies:Director rdfs:subClassOf movies:Artist .
  movies:title rdf:type owl:DatatypeProperty .
  movies:directedBy rdfs:range movies:Director .

```

Triplets représentant des données (Les Faits)
  Ces triplets décrivent des individus spécifiques (m1, m2, m3, a1) et leurs relations
  concrètes qu'on met dans le fichier owlExo2Data.ttl
  
  ```ttl
  @prefix movies: <http://www.lirmm.fr/ulliana/movies#> .
  @prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
  @prefix dbp: <http://dbpedia.org/> .

  movies:m3 movies:directedBy dbp:Alfred_Hitchcock .
  movies:m2 movies:title "Vertigo" .
  movies:m1 rdf:type movies:Movie .
  movies:a1 movies:playsIn movies:m4 .

```

\
3. En combinant le schéma (règles) et les données (faits), nous obtenons les nouvelles
  informations suivantes :


*Inférences basées sur les Domaines (rdfs:domain)
*

movies:m2 rdf:type movies:Movie . (Car m2 possède un title)
movies:m3 rdf:type movies:Movie . (Car m3 est sujet de directedBy)
movies:a1 rdf:type movies:Actor . (Car a1 est sujet de playsIn)


*Inférences basées sur les Portées (rdfs:range)
*

dbp:Alfred_Hitchcock rdf:type movies:Director . (Car il est l'objet de directedBy)

movies:m4 rdf:type movies:Movie . (Car il est l'objet de playsIn)


*Inférences basées sur la Hiérarchie (rdfs:subClassOf)
*

dbp:Alfred_Hitchcock rdf:type movies:Artist . (Car c'est un Director et tout réalisateur est un artiste)

movies:a1 rdf:type movies:Artist . (Car c'est un Actor et tout acteur est un artiste)



4.  Oui, le modèle contient bien les inférences.

1. Ajouter une propriété symétrique :

Propriété choisie : `:isFriendOf`
Dans `owlDemoSchema.ttl`, nous avons défini `:isFriendOf` comme de type `owl:SymmetricProperty`.
Dans `owlDemoData.ttl`, nous avons ajouté le fait : `:Alice :isFriendOf :Bob`.

Inférence constatée : 
En exécutant le raisonneur OWL (`RDFOWLReasoning.java`), le système déduit automatiquement que la relation inverse est vraie, générant le triplet inféré suivant :
`http://example.org/Bob http://example.org/isFriendOf http://example.org/Alice`

2. Ajouter une propriété fonctionnelle :

Propriété choisie : `:hasPrimaryDoctor`
Dans `owlDemoSchema.ttl`, nous avons défini `:hasPrimaryDoctor` comme de type `owl:FunctionalProperty`.
Dans `owlDemoData.ttl`, nous avons déclaré que le patient `:Alice_Rel` a pour médecin principal `:DrSmith` et `:DrJohnSmith`.

Inférence constatée :
En exécutant le raisonneur OWL, le système déduit que puisqu'un patient ne peut avoir qu'un seul médecin principal (propriété fonctionnelle), les deux ressources `:DrSmith` et `:DrJohnSmith` doivent en réalité représenter la même entité. Il génère les triplets d'équivalence suivants :
`http://example.org/DrSmith owl:sameAs http://example.org/DrJohnSmith`
`http://example.org/DrJohnSmith owl:sameAs http://example.org/DrSmith`

3. Ajouter une propriété inverse :

Propriétés choisies : `:isTreatedBy` comme inverse de `:treats`.
Dans `owlDemoSchema.ttl`, nous avons défini `:isTreatedBy` avec `owl:inverseOf :treats`.
Dans `owlDemoData.ttl`, nous avons déclaré le fait : `:Charlie_Rel :isTreatedBy :DrHouse`.

Inférence constatée : 
En exécutant le raisonneur OWL, le système déduit automatiquement la relation dans l'autre sens. Il génère le triplet inverse suivant :
`http://example.org/DrHouse http://example.org/treats http://example.org/Charlie_Rel`

4. Ajouter une propriété transitive :

Propriété choisie : `:isSuperiorOf`
Dans `owlDemoSchema.ttl`, nous avons défini `:isSuperiorOf` comme `owl:TransitiveProperty`.
Dans `owlDemoData.ttl`, nous avons créé une chaîne hiérarchique avec deux faits : `:AliceBoss :isSuperiorOf :BobManager` et `:BobManager :isSuperiorOf :CharlieWorker`.

Inférence constatée :
En exécutant le raisonneur OWL, le système déduit par transitivité que la relation s'étend du premier au dernier maillon de la chaîne. Il génère le triplet attendu :
`http://example.org/AliceBoss http://example.org/isSuperiorOf http://example.org/CharlieWorker`
