# frozen_string_literal: true

Rails.application.routes.draw do
  defaults format: :json do
    get 'users/me'
    put 'users/me', to: 'users#update'
    resources :tags
    resources :notifications
    resources :documents do
      resources :tags, only: :update, controller: 'documents/tags'
      collection do
        get :favorites
        get :about_to_expire
      end
    end
    post 'sign_in', to: 'sessions#create'
    post 'sign_up', to: 'registrations#create'
    resources :sessions, only: %i[index show destroy]
    resource  :password, only: %i[edit update]
    namespace :sessions do
      resource :sudo, only: %i[new create]
    end
    namespace :identity do
      resource :email,              only: %i[edit update]
      resource :email_verification, only: %i[edit create]
      resource :password_reset,     only: %i[new edit create update]
    end
  end
  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html

  # Defines the root path route ("/")
  # root "articles#index"
end
