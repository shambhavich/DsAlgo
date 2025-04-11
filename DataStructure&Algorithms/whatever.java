import java.util.Scanner;

public class whatever {
     public static class Node{
        int data = 0;
        Node left = null;
        Node right = null;

        Node(int data){
            this.data = data;
        }
    }

    static int idx = 0;
    public static Node constructTree(int[] arr){
        if(idx >= arr.length || arr[idx] == -1){
            idx++;
            return null;
        }


        Node node = new Node(arr[idx++]);
        node.left = constructTree(arr);
        node.right = constructTree(arr);

        return node;
    }

    

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        //String name = sc.nextLine();
        //int age = sc.nextInt();
        boolean isMale = sc.nextBoolean();
        boolean isMarried = sc.nextBoolean();
        int a = 5;
        int b = 10;
        String s1 = "5";
        String s2 = "a10";

        if(isMale){
            System.out.println("Mr");
            System.out.println(a+b);
            System.out.println(s1+s2);
        }
        if(isMarried==false){
            System.out.println("Miss");
        }
        else if(isMarried){
            System.out.println("Mrs");
        }
        sc.close();
        
    }
    
    
}
