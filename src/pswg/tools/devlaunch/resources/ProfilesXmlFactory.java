package pswg.tools.devlaunch.resources;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ProfilesXmlFactory {
	
	public static final String FILE = "./devlaunch_profiles.xml";
	private static final String ROOT_NODE = "DevLaunch";
	private static final String PROFILES_NODE = "Profiles";
	
	private static final String PROFILES_CHILD_NODE = "LauncherProfile";
	private static final String PROFILE_NAME = "Name";
	private static final String PROFILE_GAME = "GameDirectory";
	private static final String PROFILE_ARGS = "GameArgs";
	private static final String PROFILE_ADDRESS = "ServerAddress";
	private static final String PROFILE_PORT = "ServerPort";
	private static final String PROFILE_BG = "Background";
	private static final String PROFILE_CONSOLE = "Console";
	
	public static void save(List<LauncherProfile> profiles) throws ParserConfigurationException, TransformerException {
		File baseFile = createBaseFile();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();
		
		Node root = serializeData(profiles, document);
		document.appendChild(root);
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(baseFile);
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
		transformer.transform(source, result);
	}
	
	public static List<LauncherProfile> open(File file) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();
		
		Node root = doc.getElementsByTagName(PROFILES_NODE).item(0);
		return deserializeData(root);
	}
	
    private static Node serializeData (List<LauncherProfile> profiles, Document file) {
        Node root = file.createElement(ROOT_NODE);
        Node profilesNode = file.createElement(PROFILES_NODE);
        root.appendChild(profilesNode);
        
        for (LauncherProfile profile : profiles) {
        	Node profileDataNode = file.createElement(PROFILES_CHILD_NODE);
        	setAttribute(file, profileDataNode, PROFILE_NAME, profile.getName());
        	setAttribute(file, profileDataNode, PROFILE_GAME, profile.getGameLoc());
        	setAttribute(file, profileDataNode, PROFILE_ARGS, profile.getGameArgs());
        	setAttribute(file, profileDataNode, PROFILE_ADDRESS, profile.getServerAddress());
        	setAttribute(file, profileDataNode, PROFILE_PORT, profile.getServerPort());
        	setAttribute(file, profileDataNode, PROFILE_BG, profile.getBackground());
	        setAttribute(file, profileDataNode, PROFILE_CONSOLE, String.valueOf(profile.isConsoleEnabled()));
	        profilesNode.appendChild(profileDataNode);
        	
        }
        return root;
    }

	private static List<LauncherProfile> deserializeData(Node root) {
        return deserializeDataVersion1(root, new ArrayList<LauncherProfile>());
    }

    private static List<LauncherProfile> deserializeDataVersion1 (Node data, List<LauncherProfile> profiles) {

        for (Node node : getChildNode (data)) {
            if (node.getNodeName().equals(PROFILES_CHILD_NODE)) {
            	LauncherProfile p = new LauncherProfile();
            	p.setName(getAttributeValue(node, PROFILE_NAME));
            	p.setGameLoc(getAttributeValue(node, PROFILE_GAME));
            	p.setGameArgs(getAttributeValue(node, PROFILE_ARGS));
            	p.setServerAddress(getAttributeValue(node, PROFILE_ADDRESS));
            	p.setServerPort(getAttributeValue(node, PROFILE_PORT));
            	p.setBackground(getAttributeValue(node, PROFILE_BG));
	            p.setConsole(Boolean.parseBoolean(getAttributeValue(node, PROFILE_CONSOLE)));
	            profiles.add(p);
            }
        }
        
        return profiles;
    }

	private static String getAttributeValue (Node node, String attr) {
        try {
            if (node != null) {
                NamedNodeMap map = node.getAttributes ();
                if (map != null) {
                    node = map.getNamedItem (attr);
                    if (node != null)
                        return node.getNodeValue ();
                }
            }
        } catch (DOMException e) {
	        DialogUtils.showExceptionDialog(e);
        }
        return null;
    }

    private static void setAttribute (Document xml, Node node, String name, String value) {
        NamedNodeMap map = node.getAttributes ();
        Attr attribute = xml.createAttribute (name);
        attribute.setValue (value);
        map.setNamedItem (attribute);
    }

    private static Node[] getChildNode (Node node) {
        NodeList childNodes = node.getChildNodes ();
        Node[] nodes = new Node[childNodes != null ? childNodes.getLength () : 0];
        for (int i = 0; i < nodes.length; i++)
            nodes[i] = childNodes.item (i);
        return nodes;
    }
    
	private static File createBaseFile() {
		File baseFile = new File(FILE);
		
		if (baseFile.exists()) {
			clearFile(baseFile);
		} else {
			try {
				baseFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return baseFile;
	}
	
	private static void clearFile(File file) {
		try {
			PrintWriter writer = new PrintWriter(file);
			writer.print("");
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
