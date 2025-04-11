import java.util.Arrays;

public class GreedyAlgo {
    
    //Leetcode 948
    public int bagOfTokensScore(int[] tokens, int power) {
        Arrays.sort(tokens);
        int score=0,maxScore=0;
        int l=0,r=tokens.length-1;
        
        while(l<=r){
            if(power>=tokens[l]){
                score++;
                maxScore = Math.max(maxScore,score);
                power -= tokens[l++];
            }else if(score>=1){
                score--;
                power += tokens[r--];
            }else break;
        }
        return maxScore;
    }

    //Leetcode 881
    public int numRescueBoats(int[] people, int limit) {
        int n = people.length;
        Arrays.sort(people);
        int boats = 0;
        int i=0,j=n-1;
        while(i<=j){
            if(people[i] + people[j] <= limit){
                i++;
                j--;
            }else{
                j--;
            }
            boats++;
        }
        return boats;
    }

    //Leetcode 1328
    public String breakPalindrome(String palindrome) {
        int n = palindrome.length();
        if(n==1) return "";

        int idx=0;
        while(idx<n/2 && palindrome.charAt(idx)=='a'){
            idx++;
        }

        StringBuilder sb = new StringBuilder(palindrome);
        if(idx==n/2){
            sb.setCharAt(n-1,'b');
        }else{
            sb.setCharAt(idx,'a');
        }

        return sb.toString();
    }
}
