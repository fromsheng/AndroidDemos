package com.artion.androiddemos;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView.BufferType;

import com.artion.androiddemos.view.CollapsibleTextView;

public class TextLongDemo extends BaseActivity {
	
	private CollapsibleTextView collapsibleTextView;
	private Button btn;
	
	private boolean isChanged = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_text_long);
		
		initLayout();
		initListener();
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		super.initLayout();
		
		collapsibleTextView = (CollapsibleTextView) findViewById(R.id.collapsible);
//		collapsibleTextView.setDesc(
//				"对于易于本地增加分支，图中Git本地和服务器端结构都很灵活，所有版本都存储在一个目录中，你只需要进行分支的切换即可达到在某个分支工作的效果。 而SVN则完全不同，如果你需要在本地试验一些自己的代码，只能本地维护多个不同的拷贝，每个拷贝对应一个SVN服务器地址。举一个实际的例子，以前我所 在的小组使用SVN作为版本控制工具，当我正在试图增强一个模块，工作做到一半，由于会改变原模块的行为导致代码服务器上许多测试的失败，所以并没有提交 代码。这时候上级对我说，现在有一个很紧急的Bug需要处理， 必须在两个小时内完成。我只好将本地的所有修改diff，并输出成为一个patch文件，然后回滚有关当前任务的所有代码，再开始修改Bug的任务，等到 修改好后，在将patch应用回来。前前后后要完成多个繁琐的步骤，这还不计中间代码发生冲突所要进行的工作量。可是如果使用Git， 我们只需要开一个分支或者转回到主分支上，就可以随时开始Bug修改的任务，完成之后，只要切换到原来的分支就可以优雅的继续以前的任务。只要你愿意，每 一个新的任务都可以开一个分支，完成后，再将它合并到主分支上，轻松而优雅。",
//				BufferType.NORMAL);
		
		if(isChanged) {
			collapsibleTextView.setDesc(
					"对于易于本地增加分支，图中Git本地和服务器端结构都很灵活，所有版本都存储在一个目录中，你只需要进行分支的切换即可达到在某个分支工作的效果。 而SVN则完全不同，如果你需要在本地试验一些自己的代码，只能本地维护多个不同的拷贝，每个拷贝对应一个SVN服务器地址。举一个实际的例子，以前我所 在的小组使用SVN作为版本控制工具，当我正在试图增强一个模块，工作做到一半，由于会改变原模块的行为导致代码服务器上许多测试的失败，所以并没有提交 代码。这时候上级对我说，现在有一个很紧急的Bug需要处理， 必须在两个小时内完成。我只好将本地的所有修改diff，并输出成为一个patch文件，然后回滚有关当前任务的所有代码，再开始修改Bug的任务，等到 修改好后，在将patch应用回来。前前后后要完成多个繁琐的步骤，这还不计中间代码发生冲突所要进行的工作量。可是如果使用Git， 我们只需要开一个分支或者转回到主分支上，就可以随时开始Bug修改的任务，完成之后，只要切换到原来的分支就可以优雅的继续以前的任务。只要你愿意，每 一个新的任务都可以开一个分支，完成后，再将它合并到主分支上，轻松而优雅。",
					BufferType.NORMAL);
		}else {
			collapsibleTextView.setDesc("简单不割", BufferType.NORMAL);
		}
		isChanged = !isChanged;
		
		btn = (Button) findViewById(R.id.button1);
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isChanged) {
					collapsibleTextView.setDesc(
							"对于易于本地增加分支，图中Git本地和服务器端结构都很灵活，所有版本都存储在一个目录中，你只需要进行分支的切换即可达到在某个分支工作的效果。 而SVN则完全不同，如果你需要在本地试验一些自己的代码，只能本地维护多个不同的拷贝，每个拷贝对应一个SVN服务器地址。举一个实际的例子，以前我所 在的小组使用SVN作为版本控制工具，当我正在试图增强一个模块，工作做到一半，由于会改变原模块的行为导致代码服务器上许多测试的失败，所以并没有提交 代码。这时候上级对我说，现在有一个很紧急的Bug需要处理， 必须在两个小时内完成。我只好将本地的所有修改diff，并输出成为一个patch文件，然后回滚有关当前任务的所有代码，再开始修改Bug的任务，等到 修改好后，在将patch应用回来。前前后后要完成多个繁琐的步骤，这还不计中间代码发生冲突所要进行的工作量。可是如果使用Git， 我们只需要开一个分支或者转回到主分支上，就可以随时开始Bug修改的任务，完成之后，只要切换到原来的分支就可以优雅的继续以前的任务。只要你愿意，每 一个新的任务都可以开一个分支，完成后，再将它合并到主分支上，轻松而优雅。",
							BufferType.NORMAL);
				}else {
					collapsibleTextView.setDesc("简单不割", BufferType.NORMAL);
				}
				isChanged = !isChanged;
				collapsibleTextView.requestFocus();
			}
		});
	}

}
