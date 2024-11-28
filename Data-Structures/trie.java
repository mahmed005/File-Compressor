public class trie{

    protected trie [] children;
    protected boolean wordEnd;

    public trie(){
        wordEnd=false;
        children=new trie[26];
    }

    public void insert(trie root,String key){

        trie current=root;

        for(char c:key.toCharArray()){
            current.children[c-'a']=new trie();
            current=current.children[c-'a'];
        }
        current.wordEnd=true;
    }

    public boolean search(trie root,String key){

        trie current=root;

        for(char c:key.toCharArray()){

            if(current.children[c-'a']==null)
                return false;
        }
        return true;
    }
}