package kursach_vp;

import java.util.*;

class Perebor {
    int n, d[], t[], D[];
    
    public Perebor(int n, int d[], int t[], int D[]) {
        this.n = n;
        this.d = d;
        this.t = t;
        this.D = D;
    }
    
    Pack perform(){
    	int tek[] = new int[n]; //рабочий массив для последовательностей
    	int best[] = new int[n]; // лучшая последовательность
    	long min = 0;
	  
    	for(int i = 0; i < n; i++)
    		tek[i] = i + 1;
	  
		for(int j = 0; j < fact(n) - 1; j++) {
			int a = shtraf(n, d, t, D, tek);
			if((a < min) || (min == 0)) {
				min = a;
				for(int i = 0; i < n; i++) {
				best[i] = tek[i];
			}
			if(min == 0)
				break;
			
			Next(tek, n);
			}
		}
		
		return new Pack(Long.toString(min), Arrays.toString(best));
    }  

	void Next(int[] arr, int n) {
		int k = n - 2 , l = n - 1;
		
		while(arr[k] > arr[k + 1]) 
			k--; 
		
		while (arr[k] >= arr[l]) 
			l--;  
		
		int temp = arr[k]; 
		arr[k] = arr[l];   
		arr[l] = temp;    
		k++;
		l = n - 1;
		
		while (k < l) {
			temp = arr[k]; 
			arr[k] = arr[l];
			arr[l] = temp;
			k++;
			l--;
		}
	}  
  
	int shtraf(int n, int dk[], int tk[], int Dk[], int num[]) {
		int shtraf = 0;
		int s = 0;
		int t = 0; //фактическое завершение выполнения
		
		for(int i = 0; i < num.length; i++) {
			if(t <= dk[num[i]-1])
				t = dk[num[i]-1];
			
			t += tk[num[i]-1];
			
			if(t >= Dk[num[i]-1])
				s = Math.max(t - Dk[num[i]-1], 0);
			else
				s = 0;
			
			shtraf += s;
			if(i == num.length-1)
				break;
		}
		
		return shtraf;
	}
	
	public  int fact(int n) {
		int res = 1;
        for(int i = 1; i <= n; i++)
            res *= i;
            
        return res;
    }
}