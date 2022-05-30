Rails.application.routes.draw do
  get 'users/me'
  resources :documents do
    collection do
      get :favorites
    end
  end
  post 'sign_in', to: 'sessions#create'
  post 'sign_up', to: 'registrations#create'
  resources :sessions, only: [:index, :show, :destroy]
  resource  :password, only: [:edit, :update]
  namespace :sessions do
    resource :sudo, only: [:new, :create]
  end
  namespace :identity do
    resource :email,              only: [:edit, :update]
    resource :email_verification, only: [:edit, :create]
    resource :password_reset,     only: [:new, :edit, :create, :update]
  end
  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html

  # Defines the root path route ("/")
  # root "articles#index"
end
