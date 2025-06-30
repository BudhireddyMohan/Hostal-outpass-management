package com.example.Service;
import java.util.Stack;
import org.springframework.stereotype.Service;
import com.example.Models.Node;


@Service
public class Avl_Tree {

    public Node root=null;
    public Node getroot(){
        return root;
    }
    public void setroot(Node root){
           this.root=root;
    }

    public  int max(int a,int b){
           if(a>b){
            return a;
           }
           return b;
        }

    public int height(Node temp){
         if(temp==null){
            return 0;
         }
         return temp.height;
    }

    public Node rightrotate(Node parent){
        Node child=parent.left;
        Node t2=child.right;
        child.right=parent;
        parent.left=t2;
        child.height=max(height(child.left),height(child.right));
        parent.height=max(height(parent.left),height(parent.right));
        return child;
    }

    public Node leftrotation(Node parent){
        Node child=parent.right;
        Node t2=child.left;
        child.left=parent;
        parent.right=t2;
        child.height=max(height(child.left),height(child.right))+1;
        parent.height=max(height(parent.left),height(parent.right))+1;
        return child;
    }


    public void Insert(Node node){
     System.out.println("node studentid == " + node.getstudentId());
     System.out.println("node studentInfo == " + node.getstudent_Information());
     System.out.println("node Studenthistory stack == " + node.getoutpassHistoryEntries());

        Node newnode=new Node(node.getstudentId(),node.student_Information);
        newnode.history=node.history;
        
         
        int studentid=newnode.getstudentId();
        if(root==null){
            root=newnode;
            setroot(newnode);
            return;
        }

        Stack<Node> stack=new Stack<>();
        Node curr=getroot();
        while(true){
            stack.push(curr);
            if(studentid>curr.studentId){
                if(curr.right==null){
                    curr.right=newnode;
                    break;
                }
                curr=curr.right;
            }
            if(studentid<curr.studentId){
                if(curr.left==null){
                    curr.left=newnode;
                    break;
                }
                curr=curr.left;
            }

        }

        while(!stack.isEmpty()){
            Node ptr=stack.pop();
            ptr.height=max(height(ptr.left),height(ptr.right))+1;
            int bf=height(ptr.left)-height(ptr.right);
            if(bf>1){                                              //left heavy
                if(studentid<ptr.left.studentId){                 //left left case
                    if(stack.isEmpty()){
                        root=rightrotate(ptr);
                        setroot(root);
                        break;
                    }else{
                        Node parentptr=stack.peek();
                        if(parentptr.left==ptr){
                            parentptr.left=rightrotate(parentptr.left);
                        }else{
                            parentptr.right=rightrotate(parentptr.right);
                        }
                    }
                }else{                                             //left right case
                     ptr.left=leftrotation(ptr.left);
                     if(stack.isEmpty()){
                        root=rightrotate(ptr);
                        setroot(root);
                        break;
                     }else{
                        Node parentptr=stack.peek();
                        if(parentptr.left==ptr){
                            parentptr.left=rightrotate(parentptr.left);
                        }else{
                            parentptr.right=rightrotate(parentptr.right);
                        }
                     }
                }
            }
            if(bf<-1){                                               //right heavy
                if(studentid>ptr.right.studentId){                  //right right case
                    if(stack.isEmpty()){
                        root=leftrotation(ptr);
                        setroot(root);
                        break;
                    }
                    else{
                        Node parentptr=stack.peek();
                        if(parentptr.left==ptr){
                           parentptr.left=leftrotation(ptr);
                        }else{
                            parentptr.right=leftrotation(ptr);
                        }
                    }
                }
                else{                                           //right left case
                      ptr.right=rightrotate(ptr.right);
                      if(stack.isEmpty()){
                        root=leftrotation(ptr);
                        setroot(root);
                        break;
                      }
                      else{
                        Node parentptr=stack.peek();
                        if(parentptr.right==ptr){
                            parentptr.right=leftrotation(ptr);
                        }
                        else{
                            parentptr.left=leftrotation(ptr);
                        }
                      }
                }
            }

        }   
    }



    //-------------------------------------------------------Inorder Traversal-------------------------------------
    public void viewroot(){
         System.out.println("Root="+getroot().studentId);
    }
}
