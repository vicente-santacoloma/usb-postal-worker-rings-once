import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.File;
import java.util.*;
import java.io.IOException;

public class Main
 {

	private String inFile;
	private String outFile;
	private	DiGraphHash digrafo;
	
	public Main (String inF, String outF)
	{
	
		digrafo = new DiGraphHash();
		inFile = inF;
		outFile = outF;
	}
	
	/** Metodo para procesar  y ejecutar el algoritmo Alcance
	*
	*/
	private int procesar ()
	{
	
		try {
		
			digrafo.read(inFile);
			Algoritmo alg = new Alcance ();
			digrafo = (DiGraphHash)alg.ejecutar((DiGraph)digrafo);
			digrafo.write(outFile);
		
		} catch (Exception ioe) 
		{
			return -1;
		}
		
		return 0;
	}
	
	private int procesarPostalWorker () 
	{
		String linea = "";
		String camino = "";
		BufferedReader in = null;
		PrintStream out = null;
		double costo = 0.0;
		
		try {
		
			in = new BufferedReader (new FileReader(inFile));
			out = new PrintStream(outFile);
			
			while ((linea = in.readLine()) != null) {
				
				if (linea.equals("deadend")) {
					digrafo.readPostalWorker(camino);
					Algoritmo alg = new MinCost();
					digrafo = (DiGraphHash)alg.ejecutar((DiGraph)digrafo);	
					costo = ((MinCost) alg).MinCostWorker();
					out.println((int)costo);
					out.flush();
					digrafo = new DiGraphHash();
					camino = "";
				
				} else {
					camino = camino + linea + " ";
				}
			}
		
		
		} catch (Exception ioe) {
			
			return -1;
		}
		return 0;
	}
	
	 public static void main(String[] args) 
	 {
        if (args.length != 2) 
		{
            System.exit(-1);
        }

        String fileIn = args[0];
        String fileOut = args[1];

        Main m = new Main(fileIn, fileOut);

        System.exit(m.procesarPostalWorker());
    }
}