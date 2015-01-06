package cc.kune.bootstrap.client.actions.gwtui;

import org.gwtbootstrap3.client.ui.base.HasIcon;
import org.gwtbootstrap3.client.ui.constants.IconType;

import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;

import com.google.gwt.user.client.ui.UIObject;

public abstract class AbstractBSChildGuiItem extends AbstractChildGuiItem {

  public AbstractBSChildGuiItem(final ButtonDescriptor descriptor) {
    super(descriptor);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIcon(java.lang.Object)
   */
  @Override
  protected void setIcon(final Object icon) {
    if (icon instanceof IconType) {
      final UIObject widget = descriptor.isChild() ? child : getWidget();
      if (widget instanceof HasIcon) {
        ((HasIcon) widget).setIcon((IconType) icon);
      }
    } else {
      super.setIcon(icon);
    }
  }
}
