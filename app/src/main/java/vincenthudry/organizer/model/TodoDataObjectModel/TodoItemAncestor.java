package vincenthudry.organizer.model.TodoDataObjectModel;

public abstract class TodoItemAncestor {
    public abstract TodoItemAncestor getParent();
    public abstract void addChild(TodoDOMItem item);
    public abstract void setupViewItem();

    public abstract void removeChild(TodoDOMItem item);
}
