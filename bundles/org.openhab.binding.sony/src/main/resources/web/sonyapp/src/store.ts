import Vue from "vue";
import Vuex from "vuex";
import DefinitionModule from "@/modules/DefinitionModule";
import TypeModule from "@/modules/TypeModule";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    definition: new DefinitionModule(),
    type: new TypeModule()
  }
});
