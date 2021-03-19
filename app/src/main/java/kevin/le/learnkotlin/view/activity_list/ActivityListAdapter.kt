package kevin.le.learnkotlin.view.activity_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kevin.le.learnkotlin.R
import kevin.le.learnkotlin.model.ActivityListItemType

class ActivityListAdapter(private val items: List<ActivityListItem>, private val onClickItemListener: OnClickItemListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class GroupViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.textView)
    }

    inner class ChildViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val type: TextView = v.findViewById(R.id.textView_type)
        val title: TextView = v.findViewById(R.id.textView_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == ActivityListItemType.GROUP.ordinal) {
            val view = inflater.inflate(R.layout.activity_list_group_item, parent, false)
            GroupViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.activity_list_child_item, parent, false)
            ChildViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getType()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = holder.itemViewType
        if (viewType == ActivityListItemType.GROUP.ordinal) {
            val vh = holder as GroupViewHolder
            val item = items[position] as GroupItem
            vh.title.text = item.name
        } else {
            val vh = holder as ChildViewHolder
            val item = items[position] as ChildItem
            vh.type.text = item.activityModel.type
            vh.title.text = item.activityModel.title
            vh.itemView.setOnClickListener {
                onClickItemListener.onClick(item.activityModel.name)
            }
        }
    }
}