package kursach_vp;

import java.util.*;

public class Genetic{
    int dk[];
    int tk[]; 
    int Dk[];  
    int n;
    LinkedList<Candidate> population = new LinkedList<Candidate>();
    final Random rand;    
    final int populationSize = 10;
    final int parentUsePercent = 10;
    
	public Genetic(int n, int dk[], int tk[], int Dk[]) {  
        this.n = n;
        this.dk = dk;
        this.tk = tk;
        this.Dk = Dk;
        rand = new Random();
    }
    
    void firstGen(){
    	for(int i = 0; i < populationSize; i++) {
    		Candidate c = new Candidate();
            c.random();             
            population.add(c);
        }        
    }  
    
    void produceNextGen() {
        LinkedList<Candidate> newpopulation = new LinkedList<Candidate>();                
        
        while (newpopulation.size() < populationSize * (1.0 - (parentUsePercent/100.0))) { 
            int size = population.size(); 
            int i = rand.nextInt(size);
            int j, k, l; 
            j = k = l = i; 
            
            while (j == i) 
            	j = rand.nextInt(size);
            
            while (k == i || k == j) 
                k = rand.nextInt(size);
            
            while (l == i || l == j || k == l) 
                l = rand.nextInt(size);
            
            Candidate c1 = population.get(i);
            Candidate c2 = population.get(j);
            Candidate c3 = population.get(k);
            Candidate c4 = population.get(l);
            int f1 = c1.shtraf();
            int f2 = c2.shtraf();
            int f3 = c3.shtraf();
            int f4 = c4.shtraf();
            Candidate w1, w2;
            
            if (f1 < f2)
                w1 = c1;        
            else 
                w1 = c2;            
           
            if (f3 < f4)
                w2 = c3;            
            else  
                w2 = c4;
           
            Candidate[] Childs = newChilds(w1, w2);
            Candidate child1, child2;
            child1 = Childs[0];
            child2 = Childs[1]; 
            mutate(child1); 
            mutate(child2);
            boolean ischildGood1 = false;
            boolean ischildGood2 = false;
            int a = child1.shtraf();
            int b = w1.shtraf();
            ischildGood1 = a <= b;
            a = child2.shtraf();
            b = w2.shtraf();
            ischildGood2 = a <= b;            
            newpopulation.add( ischildGood1 ? child1 : w1);
            newpopulation.add( ischildGood2 ? child2 : w2);
        }
        
        int j = (int)(populationSize*parentUsePercent/100.0);
        for(int i = 0; i < j; i++)
            newpopulation.add( population.get(i));
                           
        population=newpopulation;   
    }
    
    Candidate[] newChilds(Candidate c1, Candidate c2) {
    	int a = n; 
    	Candidate child1 = new Candidate();
    	Candidate child2 = new Candidate();
    	for(int i = 0; i < a; i++) {
    		child1.genotype[i] = c1.genotype[i];
    		child2.genotype[i] = c2.genotype[i];
    	}
    	
    	return new Candidate[]{child1,child2} ;     
	}  
    
    void mutate(Candidate c){
    	int a = n;
        int i;
        int j;
        if(n < 2) {
        	i = 0;
        	j = 1;
        }
        else {
	        i = rand.nextInt(a-1);
	        j= i+1;
        }
        
        int b;
        b = c.genotype[i];
        c.genotype[i] = c.genotype[j];
        c.genotype[j] = b;
    }
    
    Pack perform2() {
        Genetic ga = new Genetic(n, dk, tk, Dk);
        ga.firstGen();
        ga.start();  
        int best[] = new int[n];
        int min = 0;
        int a = 0;
        
        for(int i = 0; i < populationSize; i++){
	        Candidate c = ga.population.get(i);
	        a = c.shtraf();
	        if((a < min)||(min == 0)) {
	            min = a;
	            best = c.genotype;
	        }
        }	
        
        ga.start();
        for(int i = 0; i < best.length; i++)
        	best[i] += 1;
        
        return new Pack(Long.toString(min), Arrays.toString(best));
    }
    
    void start() {
        final int maxSteps = 100000;
        int count = 0;
        while(count < maxSteps){
            count++;
            produceNextGen();
        }     
    }
    
	public class Candidate implements Comparable<Candidate> {
		public final int size = n;
        public int[] genotype;
        
        public Candidate() {
            genotype = new int[size];
            for(int i = 0; i < size; i++)
                genotype[i] = i;

        }
        
        void random() {  
        	int a=n;
            for(int k = 0; k < a/2; k++) {
                  int i = rand.nextInt(a);
                  int j= rand.nextInt(a);
                  int t;
                  t = genotype[i];
                  genotype[i]= genotype[j];
                  genotype[j] = t;
            }
        }
        
       int shtraf() {
            int sum = 0;
            int t = 0;
            int s = 0;
            for(int i = 0; i < genotype.length; i++) {
            	if(t <= dk[genotype[i]])
            		t = dk[genotype[i]];

                t += tk[genotype[i]];
                
                if(t >= Dk[genotype[i]]) {
                	s = (Math.max(t - Dk[genotype[i]], 0));
                	sum += s;
                }
            }
            
            return sum;
        }
       
        public int compareTo(Candidate o){
            int f1 = this.shtraf();
            int f2 = o. shtraf();
            if (f1 < f2)
                return -1;
            else if (f1 > f2)
                return 1;
            else
                return 0;
        }    
    }
}
