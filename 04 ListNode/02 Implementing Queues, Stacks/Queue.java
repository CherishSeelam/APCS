// Name: Cherish Seelam  
// Date: 12/3/23
public class Queue 
{
    private ListNode front;
    private ListNode rear;

    public void add(Object obj) 
    {
        ListNode node = new ListNode(obj, null);
        if (rear != null) 
        {
            rear.setNext(node);
        }
        rear = node;
        if (front == null) 
        {
            front = rear;
        }
    }

    public Object remove() 
    {
        if (front == null) 
        {
            return null;
        }
        Object data = front.getValue();
        front = front.getNext();
        if (front == null) 
        {
            rear = null;
        }
        return data;
    }

    public Object peek() 
    {
        if (front == null) 
        {
            return null;
        } 
        else 
        {
            return front.getValue();
        }
    }

    public boolean isEmpty() 
    {
        if (front == null) 
        {
            return true;
        } 
        else 
        {
            return false;
        }
    }

    public String toString() 
    {
        String str = "[";
        for (ListNode pointer = front; pointer != null; pointer = pointer.getNext()) 
        {
            str += pointer.getValue();
            if (pointer.getNext() != null) 
            {
                str += ", ";
            }
        }
        str += "]";
        return str;
    }
}
