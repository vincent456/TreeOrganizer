package vincenthudry.organizer.model.TodoDataObjectModel;

import java.util.ArrayList;

public abstract class TodoItemAncestor {
    protected ArrayList<TodoDOMItem> children;

    public abstract TodoItemAncestor getParent();
    public abstract void addChild(TodoDOMItem item);
    public abstract void setupViewItem();

    public abstract void removeChild(TodoDOMItem item);
    public abstract int getChildIndex(TodoDOMItem item);
    public abstract TodoDOMItem getIthChild(int i);

    public ArrayList<TodoDOMItem> getChildren() {
        return children;
    }

    public void addChild(int index,TodoDOMItem child) {
        children.add(index,child);
        child.setParent(this);
    }
    public void addChild(int index, ArrayList<TodoDOMItem> children){
        this.children.addAll(index,children);
        for(TodoDOMItem child : children){
            child.setParent(this);
        }
    }
}
