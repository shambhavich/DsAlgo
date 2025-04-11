import java.util.HashMap;

public class LL_questions {
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    //Leetcode 876
    public ListNode middleNode(ListNode head) {
        if(head==null) return head;

        ListNode slow = head;
        ListNode fast = head;

        while(fast!=null && fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public ListNode middleNode2(ListNode head) {
        if(head==null) return head;

        ListNode slow = head;
        ListNode fast = head;

        while(fast.next!=null && fast.next.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    //Leetcode 206
    public ListNode reverseList(ListNode head) {
        if(head==null) return head;

        ListNode curr = head;
        ListNode next = null;
        ListNode prev = null;

        while(curr!=null){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    //Leetcode 234
    //Method 1 - 6ms
    public boolean isPalindrome01(ListNode head) {
        if(head==null || head.next==null) return true;

        ListNode curr1 = head;
        ListNode midNode = middleNode2(head);
        ListNode mHead = midNode.next;
        midNode.next = null;

        ListNode curr2 = reverseList(mHead);
        while(curr2!=null){
            if(curr1.val!=curr2.val) return false;
            curr1 = curr1.next;
            curr2 = curr2.next;
        }
        midNode.next = reverseList(curr2);
        return true;
    }

    //Method 2 - A lil faster approach - 3ms
    public boolean isPalindrome(ListNode head) {
        ListNode pre = null, prepre = null;
        ListNode slow  = head, fast = head;

        while(fast!=null && fast.next!=null){
            prepre = pre;
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
            pre.next = prepre;
        }

        if(fast!=null) slow = slow.next;

        while(slow!=null){
            if(slow.val!=pre.val) return false;
            slow = slow.next;
            pre = pre.next;
        }
        return true;
    }

    //Reverse data of Linked List
    public ListNode reverseData(ListNode head){
        if(head==null || head.next==null) return head;

        ListNode pre = null, prepre = null;
        ListNode slow  = head, fast = head;

        while(fast!=null && fast.next!=null){
            prepre = pre;
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
            pre.next = prepre;
        }
        ListNode l1 = pre;
        ListNode l2 = (fast==null)? slow : slow.next;
        prepre = slow;
        while(l1!=null){
            int temp = l1.val;
            l1.val = l2.val;
            l2.val = temp;
            l1 = l1.next;
            l2 = l2.next;
            pre.next = prepre;
            prepre = pre;
            pre = l1;
        }
        return prepre;
    }

    //Leetcode 143
    public void reorderList(ListNode head) {
        if(head.next==null) return;

        ListNode midNode = middleNode2(head);
        ListNode mHead = midNode.next;
        midNode.next = null;

        mHead = reverseList(mHead);
        ListNode h1=null,h2=null,curr=head;

        while(mHead!=null){
            h1 = curr.next;
            h2 = mHead.next;
            curr.next = mHead;
            mHead.next = h1;
            curr = h1;
            mHead = h2;
        }
    }

    //Leetcode 143 - Contd. - Reordered List to original list
    public ListNode reorderListToOriginalList(ListNode head){
        if(head==null||head.next==null) return head;

        ListNode c1 = head;
        ListNode c2 = head.next;

        ListNode t1 = c1;
        ListNode t2 = c2;

        while(t2!=null && t2.next!=null){
            t1.next = t2.next;
            t2.next = t1.next.next;
            t1 = t1.next;
            t2 = t2.next;
        }

        c2 = reverseList(c2);
        t1.next = c2;
        return c1;
    }

    //Leetcode 21
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1==null || list2==null){
            return (list1==null)? list2 : list1;
        }

        ListNode dummy = new ListNode (-101);
        ListNode prev = dummy;
        ListNode curr1 = list1,curr2 = list2;

        while(curr1!=null && curr2!=null){
            if(curr1.val>=curr2.val){
                prev.next = curr2;
                curr2 = curr2.next;
            }else{
                prev.next = curr1;
                curr1 = curr1.next;
            }
            prev = prev.next;
        }

        prev.next = (curr1==null)? curr2:curr1;
        ListNode head = dummy.next;
        dummy.next = null;
        return head;
    }

    //Leetcode 148
    public ListNode sortList(ListNode head) {
        if(head==null || head.next==null) return head;

        ListNode midNode = middleNode2(head);
        ListNode mHead = midNode.next;
        midNode.next = null;

        return mergeTwoLists(sortList(head),sortList(mHead));
    }

    //Leetcode 23
    //Method 1
    public ListNode mergeKLists01(ListNode[] lists) {
        if(lists.length==0) return null;

        ListNode refList = null;
        for(int i=0;i<lists.length;i++){
            refList = mergeTwoLists(refList,lists[i]);
        }
        return refList;
    }

    //Method 2
    public ListNode mergeKLists(int si,int ei,ListNode[] lists){
        if(si==ei) return lists[si];
        int mid = (si+ei)/2;
        ListNode l1 = mergeKLists(si,mid,lists);
        ListNode l2 = mergeKLists(mid+1,ei,lists);

        return mergeTwoLists(l1,l2);
    }
    public ListNode mergeKLists02(ListNode[] lists) {
        if(lists.length==0) return null;

        return mergeKLists(0,lists.length-1,lists);
    }

    //Leetcode 141 - Detect loop in Linked List
    public boolean hasCycle(ListNode head) {
        if(head==null) return false;

        ListNode slow = head;
        ListNode fast = head;

        while(fast!=null && fast.next!= null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow==fast) return true;
        }

        return false;
    }

    //Leetcode 142
    public ListNode detectCycle(ListNode head) {
        if(head==null || head.next==null) return null;
        ListNode slow = head,fast = head;

        while(fast!=null && fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow==fast) break;
        }

        if(slow!=fast) return null;

        slow = head;
        while(slow!=fast){
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    //Leetcode 160
    //Method 1
    public int lengthOfLL(ListNode head){
        int count = 0;
        ListNode temp = head;
        while(temp!=null){
            count++;
            temp = temp.next;
        }
        return count;
    }
    public ListNode getIntersectionNode01(ListNode headA, ListNode headB) {
        if(headA==null || headB==null) return null;

        int l1 = lengthOfLL(headA);
        int l2 = lengthOfLL(headB);
        int d = l1-l2;
        ListNode biggerList = (d>0)? headA:headB;
        ListNode smallerList = (d<=0)? headA:headB;

        d = Math.abs(d);
        while(d-->0){
            biggerList = biggerList.next;
        }

        while(biggerList!=smallerList){
            biggerList = biggerList.next;
            smallerList = smallerList.next;
        }
        return biggerList;
    }

    //Method 2
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA==null || headB==null) return null;

        ListNode curr= headA;
        while(curr.next!=null) curr = curr.next;

        curr.next = headB;
        ListNode ans = detectCycle(headA);
        curr.next = null;
        return ans;
    }

    //Leetcode 19
    public ListNode removeNthFromEnd(ListNode head, int n) {
        int len = lengthOfLL(head);
        if(len==n) return head.next;
        len = len - n;
        ListNode curr = head;
        ListNode prev = null;
        while(len-->0){
            prev = curr;
            curr = curr.next;
        }
        prev.next = curr.next;
        curr.next = null;
        return head;
    }

    //Leetcode 25
    public ListNode reverseKGroup(ListNode head, int k) {
        if(head==null||head.next==null||k==1) return head;

        ListNode oh = null;
        ListNode ot = null;
        ListNode th = null;
        ListNode tt = null;
        int len = lengthOfLL(head);
        ListNode curr = head;
        int tempK;
        while(len>=k){
            tempK = k;
            while(tempK-->0){
                ListNode next = curr.next;
                if(th==null)
                   tt = curr;
                curr.next = th;
                th = curr;
                curr = next;
            }
            if(oh==null){
                oh = th;
                ot = tt;
            }else{
                ot.next = th;
                ot = tt;
            }
            th = null;
            tt = null;
            len -= k;
        }
        ot.next = curr;
        return oh;
    }

    //Leetcode 92
    public ListNode reverseBetween(ListNode head, int left, int right) {
        int i = 1;
        ListNode curr = head;
        ListNode prev = null;
        ListNode h = null,t = null;
        while(curr!=null){
            if(i<left){
                prev = curr;
                curr = curr.next;
                i++;
            }
            else if(i>right) break;
            else{
                while(i>=left && i<=right){
                    if(h==null) t = curr;
                    ListNode next = curr.next;
                    curr.next = h;
                    h = curr;
                    curr = next;
                    i++;
                }
                if(prev!=null) prev.next = h;
                t.next = curr;
            }
            
        }
        return (left>1)? head : h;
    }

    //Leetcode 138
    class Node {
        int val;
        Node next;
        Node random;
    
        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
    public Node copyRandomList(Node head) {
        if(head==null) return head;

        Node curr = head;
        Node temp = null;

        while(curr!=null){
            temp = curr.next;
            curr.next = new Node(curr.val);
            curr.next.next = temp;
            curr = temp;
        }

        curr = head;

        while(curr!=null){
            curr.next.random = (curr.random!=null)? curr.random.next:curr.random;
            curr = curr.next.next;
        }
        curr = head;
        Node copyHead = head.next;
        temp = copyHead;
        while(curr!=null){
            curr.next = temp.next;
            temp.next = (temp.next!=null)? temp.next.next:temp.next;
            curr = curr.next;
            temp = temp.next;
        }
        return copyHead;
    }

    //https://www.geeksforgeeks.org/problems/segregate-even-and-odd-nodes-in-a-linked-list5035/1
    ListNode divide(int N, ListNode head){
        if(N==1) return head;
    
        ListNode dummyOdd = new ListNode(-1);
        ListNode odd = dummyOdd;
        ListNode dummyEven = new ListNode(-1);
        ListNode even = dummyEven;
        ListNode curr = head;
        
        while(curr!=null){
            if(curr.val%2==0){
                even.next = curr;
                even = even.next;
            }else{
                odd.next = curr;
                odd = odd.next;
            }
            curr = curr.next;
        }
        even.next = dummyOdd.next;
        odd.next = null;
        dummyOdd.next = null;
        head = dummyEven.next;
        return head;
    }

    //Leetcode 146
    class LRUCache {
        class Node{
            int key;
            int value;
            Node prev;
            Node next;

            Node(int key,int value){
                this.key = key;
                this.value = value;
            }
        }

        private Node head = null;
        private Node tail = null;
        private int size = 0;
        private int maxCapacity = 0;
        HashMap<Integer,Node> map;

        public LRUCache(int capacity) {
            this.maxCapacity = capacity;
            map = new HashMap<>();
        }

        private void addFirst(Node node){
            if(this.size==0){
                this.head = node;
                this.tail = node;
            }else{
                this.head.next = node;
                node.prev = this.head;
                this.head = node;
            }
            this.size++;
        }
        private Node removeNode(Node node){
            if(this.size==1){
                this.head = null;
                this.tail = null;
                this.size--;
                return node;
            }
            if(this.tail==node) return removeLast();
            else{
                Node prevNode = node.prev;
                Node nextNode = node.next;

                prevNode.next = nextNode;
                nextNode.prev = prevNode;

                node.prev = null;
                node.next = null;
                this.size--;
                return node;
            }
        }
        private Node removeLast(){
            if(this.size==1){
                Node node = this.tail;
                this.tail = null;
                this.head = null;
                this.size--;
                return node;
            }

            Node lastNode = this.tail;
            Node secondLastNode = this.tail.next;
            secondLastNode.prev = null;
            lastNode.next = null;
            this.tail = secondLastNode;
            this.size--;
            return lastNode;
        }
        private void makeMostRecent(Node node){
            if(this.head == node) return;

            removeNode(node);
            addFirst(node);
        }
        public int get(int key) {
            if(!map.containsKey(key)) return -1;

            Node node = map.get(key);
            makeMostRecent(node);
            return node.value;        
        }
        
        public void put(int key, int value) {
            if(map.containsKey(key)){
                Node node = map.get(key);
                node.value = value;
                makeMostRecent(node);
            }else{
                if(this.size==maxCapacity){
                    Node node = removeLast();
                    map.remove(node.key);
                }
                Node node = new Node(key,value);
                addFirst(node);
                map.put(key,node);
            }
        }
    }
}
