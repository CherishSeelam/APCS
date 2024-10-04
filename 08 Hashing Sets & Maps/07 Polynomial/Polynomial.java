 // Name: Cherish Seelam   
 // Date: 3/12/24
import java.util.*;
interface PolynomialInterface
{
   public void makeTerm(Integer exp, Integer coef); 
   public Map<Integer, Integer> getMap();
   public double evaluateAt(double x);
   //precondition: both polynomials are in standard form
   //postcondition: terms with zero disappear. If all terms disappear (the size is zero), 
   //               add pair (0,0).
   public Polynomial add(Polynomial other);
   //precondition: both polynomials are in standard form
   //postcondition: terms with zero disappear. If all terms disappear (the size is zero), 
   //               add pair (0,0)
   public Polynomial multiply(Polynomial other);
   public String toString();
}

class Polynomial implements PolynomialInterface
{
    private TreeMap<Integer, Integer> terms;

    public Polynomial() 
    {
        terms = new TreeMap<Integer, Integer>();
    }

    public void makeTerm(Integer exp, Integer coef) 
    {
        terms.put(exp, coef);
    }

    public Map<Integer, Integer> getMap() 
    {
        return terms;
    }

    public double evaluateAt(double x) 
    {
        double result = 0;
        for (int exp : terms.keySet()) 
        {
            result += terms.get(exp) * Math.pow(x, exp);
        }
        return result;
    }

    public Polynomial add(Polynomial other) 
    {
        Polynomial result = new Polynomial();
        TreeSet<Integer> allExponents = new TreeSet<>(Collections.reverseOrder());
        allExponents.addAll(this.terms.keySet());
        allExponents.addAll(other.getMap().keySet());

        for (Integer exp : allExponents) 
        {
            int coef1 = this.terms.getOrDefault(exp, 0);
            int coef2 = other.getMap().getOrDefault(exp, 0);
            int sum = coef1 + coef2;
            if (sum != 0) 
            {
                result.makeTerm(exp, sum);
            }
        }
        return result;
    }

    public Polynomial multiply(Polynomial other) 
    {
        Polynomial result = new Polynomial();
        for (Integer exp1 : this.terms.keySet()) 
        {
            for (Integer exp2 : other.getMap().keySet()) 
            {
                int newExp = exp1 + exp2;
                int newCoef = this.terms.get(exp1) * other.getMap().get(exp2);
                result.makeTerm(newExp, result.getMap().getOrDefault(newExp, 0) + newCoef);
            }
        }
        return result;
    }

    public String toString() 
    {
      String toRet = "";
      if (terms.isEmpty())
      {
         return "";
      }
      for (int x : terms.keySet())
      {
         if (terms.get(x) == 0)
         {
            toRet = "";
         }
         else          
         {
            if (x == 0)
            {
               toRet = toRet + "" + terms.get(x);
            }
            else if (x == 1)
            {
               if(terms.get(x) == 1)
               {
                  toRet= "x + " + toRet;
               }
               else
               {
                  toRet = terms.get(x) + "x + " + toRet;
               }
            }
            else
            {
               if(terms.get(x) == 1) 
               {
                  toRet = "x^" + x + " + " + toRet;
               }
               else 
               {
                  toRet = terms.get(x) + "x^" + x + " + " + toRet;
               }
            } 
         }
      }
      return toRet;
   }
}