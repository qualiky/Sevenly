package com.example.sevenly

class Constants {
    fun defaultExercise() : ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()

        //this arraylist consists of all the exercise details that will be displayed each time in the screen
        exerciseList.add(ExerciseModel(1,"Abdominal Crunch",R.drawable.ic_abdominal_crunch,false,false,R.raw.ballpoint_jester))
        exerciseList.add(ExerciseModel(2,"Buttkicks",R.drawable.ic_buttkicks,false,false, R.raw.cut_ya_off_toby))
        exerciseList.add(ExerciseModel(3,"Crutches",R.drawable.ic_crutches,false,false, R.raw.want_it_dylan))
        exerciseList.add(ExerciseModel(4,"High Knee running",R.drawable.ic_high_knees_running_in_place,false,false, R.raw.debate_dylan_sits))
        exerciseList.add(ExerciseModel(5,"Jumping Jacks",R.drawable.ic_jumpingjacks,false,false, R.raw.forward_thots))
        exerciseList.add(ExerciseModel(6,"Lunges",R.drawable.ic_lunges,false,false, R.raw.highlights_reel_dylan))
        exerciseList.add(ExerciseModel(7,"Planks",R.drawable.ic_planks,false,false, R.raw.humidity_dylan))
        exerciseList.add(ExerciseModel(8,"Push up and Rotation",R.drawable.ic_push_up_and_rotation,false,false, R.raw.litty_apollo))
        exerciseList.add(ExerciseModel(9,"Pushups",R.drawable.ic_pushups,false,false, R.raw.lowkey_vibes_toby))
        exerciseList.add(ExerciseModel(10,"Situps",R.drawable.ic_situps,false,false, R.raw.new_rover_apollo))
        exerciseList.add(ExerciseModel(11,"Squat",R.drawable.ic_squat,false,false, R.raw.persistence_dylan))
        exerciseList.add(ExerciseModel(12,"Step up onto chair",R.drawable.ic_step_up_onto_chair,false,false, R.raw.powerwalk_toby))
        exerciseList.add(ExerciseModel(13,"Wallsit",R.drawable.ic_wallsit,false,false, R.raw.some_thing_rambutan))

        return exerciseList
    }
}