// This is a library to be used to represent a Graph and various measurments for a Graph
//  and to perform optimization using Particle Swarm Optimization (PSO)
//    Copyright (C) 2008, 2015 
//       Patrick Olekas - polekas55@gmail.com
//       Ali Minai - minaiaa@gmail.com
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//   You should have received a copy of the GNU General Public License
//   along with this program.  If not, see <http://www.gnu.org/licenses/>.
package psograph.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.util.Iterator;
import java.util.Vector;

import psograph.graph.CalculatedGraph;

/** 
 * @deprecated
 * @author Owner
 *
 */
public class MfileWriter {

	String m_baseFileNames[];
	Vector<GraphLabel> m_labels;
	
	protected MfileWriter()
	{
		

	}
	
    protected static void generateAdjMatrix(BufferedWriter bufferedWriter, int count) throws Exception
    {
    	
    	bufferedWriter.write("A = [ \n");
    	int i,j;
    	for(i=0; i < count; i++)
    	{
    		for(j=0; j < count; j++)
    		{
    			if(i==j)
    			{
    				bufferedWriter.write("0 ");
    			}
    			else
    			{
    				bufferedWriter.write("1 ");
    			}
    		}
    		bufferedWriter.write(" ;\n");
    	}
    	bufferedWriter.write(" ];\n");
    	bufferedWriter.flush();
    	
    }
	
	
	void printStartOfCalculatedMFile(Vector<BufferedWriter> writers) throws Exception
	{
		Iterator<BufferedWriter> iter = writers.iterator();
		
	    for (; iter.hasNext(); ) {
	    	BufferedWriter writer = iter.next();
	    	writer.write("xy = [ \n");
	    }
	}
	
    // It is also possible to filter the list of returned files.
    // This example does not return any files that start with `.'.
    FilenameFilter filter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.endsWith(".CalculatedGraph");
        }
    };
	
    public void processSeedDirectory(File seedDirectory) throws Exception
    {
		File graphDirectory = new File(seedDirectory.getAbsolutePath()+"\\graphs");
		
		if(!graphDirectory.exists())
		{
			return;
			
		}
    	
    	File calculatedGraphs[] = graphDirectory.listFiles(filter);
		if (calculatedGraphs == null)
			throw new Exception ("SeedDirectory is empty");
		
		System.out.println("Working on SeedDirectory - "+ seedDirectory.getAbsolutePath());
    	
    	Vector<BufferedWriter> writers = new Vector<BufferedWriter>();
    	int j;

    	
    	//To make it easier to put multiple of these m files in the same directory add unique id to file name
    	String t = seedDirectory.getName();
    	String tt[] = t.split("_");
    	
    	
    	
    	for(j=0; j < m_baseFileNames.length; j++)
    	{
	    	File m_File = new File(seedDirectory.getAbsolutePath()+"\\"+m_baseFileNames[j]+"_"+tt[1]+".m") ;
	    	BufferedWriter m_bufWriter = new BufferedWriter(new FileWriter(m_File));
	    	writers.add(m_bufWriter);
    	}


    	
    	int m_graphCount = 0;
		
		

		
		int maxCalculatedGraphs = calculatedGraphs.length;
		//for loop to go over each .calculatedGraph file
		printStartOfCalculatedMFile(writers);
		
		for(j=0; j < maxCalculatedGraphs; j++)
		{
			CalculatedGraph graph =  null;

			//System.out.println("CalculatedGraph was retrieved Graph " + calculatedGraphs[j].toString());
			graph = psograph.util.Util.streaminCalculatedGraph(calculatedGraphs[j]);
			
			boolean is_last = j == (maxCalculatedGraphs -1);
			
			printDataEntryCalculatedMfile(writers, graph, is_last);
			

			
			m_graphCount++;
			

		}
		printEndOfCalculatedMFile(writers, m_graphCount);
    }
	
	void printDataEntryCalculatedMfile(Vector<BufferedWriter> writers, CalculatedGraph calcGraph, boolean is_last) throws Exception
	{
		
	
	}
	
	void printEndOfCalculatedMFile(Vector<BufferedWriter> writers, int numGraphs) throws Exception
	{
		
	
	}
	
}
