/* 
 * This file is part of OppiaMobile - https://digital-campus.org/
 * 
 * OppiaMobile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * OppiaMobile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with OppiaMobile. If not, see <http://www.gnu.org/licenses/>.
 */

package org.digitalcampus.oppia.widgets.quiz;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.maf.oppia.R;
import org.digitalcampus.mobile.quiz.model.Response;
import org.digitalcampus.oppia.activity.PrefsActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class MultiSelectWidget extends QuestionWidget {

	public static final String TAG = MultiSelectWidget.class.getSimpleName();
	private LinearLayout responsesLL;
	protected SharedPreferences prefs;
	
	public MultiSelectWidget(Activity activity,  View v, ViewGroup container) {
		init(activity,container,R.layout.widget_quiz_multiselect, v);
		prefs = PreferenceManager.getDefaultSharedPreferences(activity);
	}

	@Override
	public void setQuestionResponses(List<Response> responses, List<String> currentAnswer) {
		responsesLL = (LinearLayout) view.findViewById(R.id.questionresponses);
    	responsesLL.removeAllViews();
    	
    	for (Response r : responses){
    		CheckBox chk= new CheckBox(ctx);  
    		chk.setText(r.getTitle(prefs.getString(PrefsActivity.PREF_LANGUAGE, Locale.getDefault().getLanguage())));
    		responsesLL.addView(chk);
    		Iterator<String> itr = currentAnswer.iterator();
    		while(itr.hasNext()){
    			String a = itr.next(); 
    			if(a.equals(r.getTitle(prefs.getString(PrefsActivity.PREF_LANGUAGE, Locale.getDefault().getLanguage())))){
    				chk.setChecked(true);
    			}
    		}
    	}	
	}

	@Override
	public List<String> getQuestionResponses(List<Response> responses) {
		int count = responsesLL.getChildCount();
		List<String> response = new ArrayList<String>();
		for (int i=0; i<count; i++) {
			CheckBox cb = (CheckBox) responsesLL.getChildAt(i);
			if(cb.isChecked()){
				response.add(cb.getText().toString());
			}
		}

		if(response.size() == 0){
			return null;
		} else {
			return response;
		}
	}

}
