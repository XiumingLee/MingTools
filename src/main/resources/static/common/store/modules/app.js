const state = {
  sidebar: {
    opened:  true,
  }
}

const mutations = {
  TOGGLE_SIDEBAR: state => {
    state.sidebar.opened = !state.sidebar.opened
  },
  CLOSE_SIDEBAR: (state) => {
    state.sidebar.opened = false
  }
}

const actions = {
  toggleSideBar({ commit }) {
    commit('TOGGLE_SIDEBAR')
  },
  closeSideBar({ commit }) {
    commit('CLOSE_SIDEBAR')
  },
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
