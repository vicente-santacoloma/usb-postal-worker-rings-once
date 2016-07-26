public class MinCost extends Algoritmo
{
	private DiGraphHash digrafo;
	private Lista <Vertice> nodosAbiertos;
	private double costoMin;
	private double solucion;
	
	public MinCost()
	{
		this.nodosAbiertos = new Lista<Vertice>() ; ;
	}

	public DiGraph  ejecutar(DiGraph D)
	{
		this.digrafo = (DiGraphHash) D;
		this.solucion = PostalWorker();
		return this.digrafo ;
	}
	
	public double PostalWorker()
	{
		double costo= 0.0;
		Lista<Vertice> nodosimp = digrafo.nodosimpares();
	
		if( nodosimp.size() == 2)
		{
			Vertice inicial =  nodosimp.get(0);
			Vertice ultimo = nodosimp.get(1);
			Dijkstra(inicial, ultimo);
			costo = this.sumarCostosArcos() + costoMin;
		}
		if (nodosimp.size() == 0)
		{
			costo =  this.sumarCostosArcos();
		}
		
		return (int) costo;
	}
	
	public double MinCostWorker()
	{
		return solucion;
	}
	
	public void inic ()
	{
		Object [] nodos = this.digrafo.getAllNodes();

		for (int i=0; i< nodos.length; i++)
		{
			this.nodosAbiertos.add(((Vertice)nodos[i]));
		}	
	
	}
	
	public void Dijkstra(Vertice fuente, Vertice destino)
	{
		boolean v = false;
		inic();
		fuente.asigCosto(0.0);
		
		while (!nodosAbiertos.isEmpty() && !v)
		{
			Vertice min = this.minimo();
			nodosAbiertos.remove(min);
			Lista<Vertice> sucesoresMin = sucesores(min);
			int k = sucesoresMin.size();
			
			for (int i = 0; i<k; i++){
			
				Vertice e = sucesoresMin.get(i);
				
				double costoc = min.getCosto() + digrafo.getArc2(min,e).getCosto();
				
				if (costoc <e.getCosto())
				{
					for (int u = 0; u<nodosAbiertos.size();u++){
				
						if (nodosAbiertos.get(u).equals(e)){
						
							nodosAbiertos.get(u).asigCosto(costoc);
					
						}
					}
				}
			}
			
			if (min.obtenerId().equals(destino.obtenerId())) {
			
				costoMin = costoMin + min.getCosto();
				v = true;
			}
		}
	}
	
	private Vertice minimo()
	{
		Vertice vertice = null;
		boolean v ;
		int k = nodosAbiertos.size();
		if (!nodosAbiertos.isEmpty())
		{
			vertice = nodosAbiertos.get(0);
			
			for (int i=0; i< k; i++)
			{
				if ( vertice.getCosto()> ((Vertice) nodosAbiertos.get(i)).getCosto())
				{
					vertice = nodosAbiertos.get(i);
				}
			}
		}
		return vertice;
	}
	
	public Lista<Vertice> sucesores (Vertice vertice)
	{
		Lista<Vertice> sucesores = this.digrafo.getSucesors(vertice);
		Lista<Vertice> sucesoresMin = new Lista<Vertice>();
		int k = sucesores.size();
		
		for (int i = 0; i<k; i++) {
		
			Vertice e =  sucesores.get(i);
			
			if (nodosAbiertos.contains(e)) {
			
				sucesoresMin.addi(e);
			}
		}
	
		return sucesoresMin; 		
	}
	
	public double costoArco(Vertice A, Vertice B)
	{
		double costo=0.0;
			System.out.println("arcos"+digrafo.getArc2(A,B));
			costo= digrafo.getArc2(A,B).getCosto();	
		return costo;
	}
	
	public double sumarCostosArcos()
	{
		double costos = 0.0;
		Lista<Arc> arcos = this.digrafo.getAllArcs();
		int k = arcos.size();
		
		for (int i = 0; i<k ; i++)
		{
			costos = costos + arcos.get(i).getCosto();
		}
		costos = costos / 2;
		return costos;
		
	}
	
	
}	
	