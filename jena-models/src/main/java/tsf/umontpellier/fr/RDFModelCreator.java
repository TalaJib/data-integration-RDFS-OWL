package tsf.umontpellier.fr;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

public class RDFModelCreator {

    public static void main(String[] args) {

        // Create an empty model
        Model model = ModelFactory.createDefaultModel();

        // Define namespace prefixes
        String TSW_namespace = "http://www.umontpellier.fr/traitementsemantiquedesdonnees#";
        model.setNsPrefix("tsf", TSW_namespace);
        // ... add here more namespaces if needed
        
        
        // Create resources and properties
        // Example: Patient Alice
        Resource alice = model.createResource(TSW_namespace + "P12345");
        model.add(alice, RDF.type, model.createResource(TSW_namespace + "Patient"));
        // ... add here more triples to your model
  
        
        System.out.println("Exporting the RDF Model\n");
        // export model with RDF/Turtle syntax
        model.write(System.out, "Turtle");
    }
}
