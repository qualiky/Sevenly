package com.example.sevenly

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sevenly.databinding.ItemExerciseStatusBinding

class ExerciseViewAdapter(
    val context: Context,
    val itemsArr: ArrayList<ExerciseModel>)
    : RecyclerView.Adapter<ExerciseViewAdapter.ExerciseViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemExerciseStatusBinding = ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ExerciseViewHolder(itemExerciseStatusBinding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = itemsArr[position]
        holder.tvItem.text = exercise.getId().toString()
    }

    override fun getItemCount(): Int {
        return itemsArr.size
    }



    class ExerciseViewHolder(
        itemExerciseStatusBinding: ItemExerciseStatusBinding)
        : RecyclerView.ViewHolder(itemExerciseStatusBinding.root) {
        val tvItem = itemExerciseStatusBinding.tvItem
    }
}