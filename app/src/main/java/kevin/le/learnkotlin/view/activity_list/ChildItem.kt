package kevin.le.learnkotlin.view.activity_list

import kevin.le.learnkotlin.model.ActivityDataModel
import kevin.le.learnkotlin.model.ActivityListItemType

class ChildItem(var activityModel: ActivityDataModel) : ActivityListItem() {
    override fun getType(): Int {
        return ActivityListItemType.CHILD.ordinal
    }
}