
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// interface for document elements
interface DocumentElement {
    public abstract String render();
}

class TextElement implements DocumentElement {
    private String text;

    public TextElement(String text) {
        this.text = text;
    }

    @Override
    public String render() {
        return text;
    }
}

class ImageElement implements DocumentElement {
    private String imagePath;

    public ImageElement(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String render() {
        return "[Image: " + imagePath + "]";
    }
}

class NewLineElement implements DocumentElement {
    @Override
    public String render() {
        return "\n";
    }
}

class TabSpaceElement implements DocumentElement {
    @Override
    public String render() {
        return "\t";
    }
}

// Document class
class Document {
    private List<DocumentElement> documentElements = new ArrayList<>();

    public void addElement(DocumentElement element) {
        documentElements.add(element);
    }

    public String render() {
        StringBuilder result = new StringBuilder();

        for (DocumentElement element : documentElements) {
            result.append(element.render());
        }

        return result.toString();
    }
}

// Persistence interface
interface Persistence {
    void save(String data);
}

class FileStorage implements Persistence {
    @Override
    public void save(String data) {
        try {
            FileWriter outfile = new FileWriter("document1.txt");
            outfile.write(data);
            outfile.close();

            System.out.println("Document saved to document.txt");
        } catch (IOException e) {
            System.out.println("Error unable to open file for writing");
        }
    }
}

class DBStorage implements Persistence {
    @Override
    public void save(String data) {
        // save to DB
        // for future scalability
    }
}

class DocumentEditor {
    private Document document;
    private Persistence storage;
    private String renderDocument = "";

    public DocumentEditor(Document document, Persistence storage) {
        this.document = document;
        this.storage = storage;
    }

    public void addText(String text) {
        document.addElement(new TextElement(text));
    }

    public void addImage(String imagePath) {
        document.addElement(new ImageElement(imagePath));
    }

    // Adds a new line to the document.
    public void addNewLine() {
        document.addElement(new NewLineElement());
    }

    // Adds a tab space to the document.
    public void addTabSpace() {
        document.addElement(new TabSpaceElement());
    }

    public String renderDocument() {
        if (renderDocument.isEmpty()) {
            renderDocument = document.render();
        }

        return renderDocument;
    }

    public void saveDocument() {
        storage.save(renderDocument());
    }
}

public class DocumentEditorClientLLD {
    public static void main(String args[]) {

        Document document = new Document();
        Persistence persistence = new FileStorage();

        DocumentEditor editor = new DocumentEditor(document, persistence);

        // Simulate a client using the editor with common text formatting features.
        editor.addText("Hello, world!");
        editor.addNewLine();
        editor.addText("This is a real-world document editor example.");
        editor.addNewLine();
        editor.addTabSpace();
        editor.addText("Indented text after a tab space.");
        editor.addNewLine();
        editor.addImage("picture.jpg");

        // Render and display the final document.
        System.out.println(editor.renderDocument());

        editor.saveDocument();
    }
}