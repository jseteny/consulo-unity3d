package org.mustbe.consulo.unity3d.action;

import org.mustbe.consulo.RequiredDispatchThread;
import org.mustbe.consulo.unity3d.Unity3dIcons;
import org.mustbe.consulo.unity3d.module.Unity3dModuleExtensionUtil;
import org.mustbe.consulo.unity3d.module.Unity3dRootModuleExtension;
import org.mustbe.consulo.unity3d.projectImport.Unity3dProjectUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * @author VISTALL
 * @since 03.04.2015
 */
public class SyncUnity3dProjectAction extends AnAction
{
	public SyncUnity3dProjectAction()
	{
		super(Unity3dIcons.Unity3d);
	}

	@Override
	@RequiredDispatchThread
	public void actionPerformed(AnActionEvent anActionEvent)
	{
		Project project = anActionEvent.getProject();
		if(project == null)
		{
			return;
		}
		Unity3dRootModuleExtension rootModuleExtension = Unity3dModuleExtensionUtil.getRootModuleExtension(project);
		if(rootModuleExtension == null)
		{
			return;
		}

		Unity3dProjectUtil.importOrUpdate(project, rootModuleExtension.getSdk(), null);
	}

	@Override
	public void update(AnActionEvent e)
	{
		if(!e.getPresentation().isVisible())
		{
			return;
		}

		Project project = e.getProject();
		if(project == null)
		{
			e.getPresentation().setEnabledAndVisible(false);
			return;
		}
		VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
		if(virtualFile == null || !virtualFile.equals(project.getBaseDir()))
		{
			e.getPresentation().setEnabledAndVisible(false);
		}
	}
}
